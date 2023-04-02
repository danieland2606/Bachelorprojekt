import { documentReady } from "./util.js";

Element.prototype.getId = function () { return this.getAttribute('id'); };
Map.prototype.setIfDef = function (key, val) { if (key && val) this.set(key, val); };

export class Application {
  #node;
  #pages = new Map();

  constructor() {
    const name = this.constructor.name;
    this.#init(name);
  }

  async #init(name) {
    await documentReady();
    this.#node = document.querySelector(`[application=${name}]`);
    const templates = this.#node.querySelectorAll('template[page]');
    templates.forEach(template => { this.#pages.set(template.getId(), new Page(template)); });
    window.addEventListener('popstate', event => { this.#display(event.state); });
    this.#gotoStart(location.hash);
  }

  #gotoStart(hash) {
    if (hash) {
      const args = hash.slice(1).split('?');
      this.navigate(args[0], args[1]);
    } else {
      const startPage = this.#node.querySelector('template[page=start]').getId();
      this.navigate(startPage);
    }
  }

  async navigate(page, opt) {
    const data = await this[`fetch${Application.toCamelCase(page)}`](opt);
    const state = { 'page': page, 'data': data };
    const url = new URL(location);
    url.hash = (opt !== undefined) ? `${page}?${opt}` : page;
    window.history.pushState(state, '', url);
    this.#display(state);
  }

  #display(state) {
    if (!state)
      return;
    const page = this.#pages.get(state.page);
    if (page)
      this.#node.replaceChildren(page.render(state.data));
  }

  static toCamelCase(string) {
    return string.split('-').map(word => {
      return word.charAt(0).toUpperCase() + word.slice(1);
    }).reduce((prev, curr) => prev + curr);
  }
}

export class Page {
  static #regex = /{{([^}]+)}}/g;
  #template;

  constructor(template) {
    this.#template = template;
  }

  render(data) {
    if (!data)
      return new DocumentFragment();
    const newPage = this.#template.content.cloneNode(true);
    Page.getTables(newPage).forEach((template, binding) => Page.#renderArray(template, binding, data));
    Page.getBindings(newPage).forEach(binding => Page.#renderData(binding, data));
    return newPage;
  }

  static #renderData(binding, data) {
    const property = binding.getAttribute('data');
    const values = data[property];
    binding.innerHTML = binding.innerHTML.replace(Page.#regex, match => Page.#map(match, values));
  }

  static #renderArray(template, binding, data) {
    const property = binding.getAttribute('data-array');
    const values = data[property];
    const nodes = values.map(val => Page.#renderRow(val, template.content.firstElementChild));
    binding.append(...nodes);
  }

  static #renderRow(data, template) {
    const row = template.cloneNode(true);
    row.innerHTML = row.innerHTML.replace(Page.#regex, match => Page.#map(match, data));
    return row;
  }

  static #map(placeholder, values) {
    const property = placeholder.slice(2, -2).split('\.');
    return Page.#getProperty(property, values);
  }

  static #getProperty(property, obj) {
    if (property.length === 1)
      return obj[property[0]];
    else
      return Page.#getProperty(property.slice(1), obj[property[0]]);
  }

  static getTables(page) {
    const boundTemplates = new Map();
    const bindings = page.querySelectorAll('[data-array]');
    bindings.forEach(element => boundTemplates.setIfDef(element, element.querySelector('template')));
    return boundTemplates;
  }

  static getBindings(page) {
    return page.querySelectorAll('[data]');
  }
}

export class Observable {
  #listeners = [];
  #value;

  constructor(value) {
    this.#value = value;
  }

  static toObservable(obj) {

  }

  #notify() {
    this.#listeners.forEach(listener => listener(this.#value));
  }

  subscribe(listener) {
    this.#listeners.push(listener);
  }

  bind(input) {
    input.value = this.#value;
    subscribe(() => input.value = this.#value);
    input.onkeyup = () => this.#value = input.value;
  }

  get value() {
    return this.#value;
  }

  set value(val) {
    if (val !== this.#value) {
      this.#value = val;
      this.#notify();
    }
  }

  toJSON(key) {
    return { key: this.#value };
  }
}
