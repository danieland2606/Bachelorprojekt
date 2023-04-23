import { documentReady, getId, toCamelCase, encodeArgs, decodeHash } from "./util.js";
import { Page } from "./Page.js";

export class Application {
  #node;
  #start;
  #pages;
  #data

  constructor() {
    this.#pages = new Map();
    this.#init(this.constructor.name);
  }

  async #init(name) {
    await documentReady();
    this.#node = document.querySelector(`[application=${name}]`);
    this.#start = getId(this.#node.querySelector('template[page=start]'));
    const templates = this.#node.querySelectorAll('template[page]');
    templates.forEach(template => { this.#pages.set(getId(template), new Page(template)); });
    window.addEventListener('popstate', event => { this.#update(event.state); });
    this.#gotoHash(location.hash);
  }

  async navigate(page, opt) {
    let data = {};
    try {
      data = await this[Application.toMethodName(page)](opt);
    } catch (_) { }
    const state = { 'page': page, 'data': data };
    const url = new URL(location);
    url.hash = (page + encodeArgs(opt));
    window.history.pushState(state, '', url);
    this.#display(state);
  }

  get data() {
    return this.#data;
  }

  get page() {
    return this.#node;
  }

  #gotoHash(hash) {
    if (hash) {
      const { page, args } = decodeHash(hash);
      this.navigate(page, args);
    } else
      this.navigate(this.#start);
  }

  #update(state) {
    if (!state)
      this.#gotoHash(location.hash);
    else
      this.#display(state);
  }

  #display(state) {
    const page = this.#pages.get(state.page);
    if (page) {
      this.#data = state.data;
      this.#node.replaceChildren(page.render(this.#data));
    }
  }

  static toMethodName(string) {
    return `fetch${toCamelCase(string)}`;
  }
}
