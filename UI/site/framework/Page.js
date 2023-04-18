import { Observable } from "./Observable.js";

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
        Page.getDynamicTemplated(newPage).forEach(element => Page.#renderArray(element, data));
        Page.getDynamicContent(newPage).forEach(element => Page.#renderData(element, data));
        Page.getBindings(newPage).forEach(element => Page.#bindData(element, data));
        return newPage;
    }

    static #bindData(node, data) {
        const binding = node.getAttribute('data-bind').split('\.');
        if(node.parentNode?.nodeName?.toLowerCase() === 'label') {
            const parent = node.parentNode;
            node.setAttribute('id', binding);
            parent.setAttribute('for', binding);
            parent.parentNode.append(node);
        }
        let observable = this.#getProperty(binding, data);
        if(!observable || !('bind' in observable))
            observable = new Observable('');
        observable.bind(node);
        this.#setProperty(data, binding, observable);
    }

    static #setProperty(obj, properties, value) {
        if (properties.length === 1)
            obj[properties[0]] = value
        else {
            obj[properties[0]] ??= {};
            this.#setProperty(obj[properties[0]], properties.slice(1), value);
        }
    }

    static #renderData(node, data) {
        const property = node.getAttribute('data');
        const values = data[property];
        node.innerHTML = node.innerHTML.replace(Page.#regex, match => Page.#map(match, values));
    }

    static #renderArray(node, data) {
        const template = node.querySelector('template').content.firstElementChild;
        const property = node.getAttribute('data-array');
        const values = data[property];
        const nodes = values?.map(val => Page.#renderRow(val, template));
        node.append(...(nodes ?? []));
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
        if(!property || !obj)
            return undefined;
        if (property.length === 1)
            return obj[property[0]];
        else
            return Page.#getProperty(property.slice(1), obj[property[0]]);
    }

    static getDynamicTemplated(page) {
        return page.querySelectorAll('[data-array]');
    }

    static getDynamicContent(page) {
        return page.querySelectorAll('[data]');
    }

    static getBindings(page) {
        return page.querySelectorAll('[data-bind]');
    }
}
