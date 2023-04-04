export class Observable {
    #listeners = [];
    #value;

    constructor(value) {
        if (typeof value !== 'object')
            this.#value = value;
        else
            for (const [key, val] of Object.entries(value))
                this[key] = new Observable(val);
    }

    #notify() {
        this.#listeners.forEach(listener => listener(this.#value));
    }

    subscribe(listener) {
        this.#listeners.push(listener);
    }

    bind(input) {
        input.value = this.#value;
        this.subscribe(() => input.value = this.#value);
        input.oninput = () => this.#value = input.value;
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
        if (this.#value !== undefined)
            return this.#value;
        const serializable = {};
        for (const [key, val] of Object.entries(this)) {
            if (typeof val === 'object' && #value in val)
                Object.assign(serializable, val.toJSON(key));
        }
        return key ? Object.fromEntries([[key, serializable]]) : serializable;
    }
}
