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
    window.onpopstate = e => { this.#navigate(e.state); };
    const startPage = this.#node.querySelector('template[page=start]').getId();
    this.navigate(startPage);
  }

  async navigate(id, opt) {
    const data = await this[`fetch${Application.toCamelCase(id)}`](opt);
    const page = this.#pages.get(id).render(data);
    const target = { 'id': id, 'target': data };
    window.history.pushState(target, '');
    this.#navigate(target);
  }

  #navigate(target) {
    const page = this.#pages.get(target.id);
    if (page) {
      this.#node.replaceChildren(page.render(target.data));
    }
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
  #data;

  constructor(template) {
    this.#template = template;
  }

  render(data) {
    if (data)
      this.#data = data;
    else if (!this.#data)
      return new DocumentFragment();
    const newPage = this.#template.content.cloneNode(true);
    Page.getTables(newPage).forEach((template, binding) => this.#renderArray(template, binding, this.#data));
    Page.getBindings(newPage).forEach(binding => this.#renderData(binding, this.#data));
    return newPage;
  }

  #renderData(binding, data) {
    const property = binding.getAttribute('data');
    const values = data[property];
    binding.innerHTML = binding.innerHTML.replace(Page.#regex, match => Page.#map(match, values));
  }

  #renderArray(template, binding, data) {
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
