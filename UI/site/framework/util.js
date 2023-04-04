export async function documentReady() {
  return new Promise((resolve) => {
    if (document.readyState !== 'loading') {
      resolve();
    }
    document.addEventListener('DOMContentLoaded', () => { resolve(); });
  });
}

export function getId(element) {
  return element.getAttribute('id');
}

export function capitalize(word) {
  return word.charAt(0).toUpperCase() + word.slice(1);
}

export function add(a, b) {
  return a + b;
}

export function toCamelCase(string) {
  return string.split('-').map(capitalize).reduce(add);
}
