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

function add(a, b) {
  return a + b;
}

export function toCamelCase(string) {
  return string.split('-').map(capitalize).reduce(add);
}

export function encodeCalcObject(obj) {
  let base64 = btoa(JSON.stringify(obj));
  return base64.replaceAll('+', '-').replaceAll('/', '_').replaceAll('=', '');
}

export function encodeArgs(args) {
  let urlParams = '?';
  if(typeof args === 'object')
    for(const [ name, arg ] of Object.entries(args)) {
      urlParams += `${name}=`;
      if(Array.isArray(arg)) {
        urlParams += arg.join(',');
      } else {
        urlParams += arg;
      }
      urlParams += '&';
    }
  return urlParams.slice(0, -1);
}

export function decodeArgs(argString) {
  if(!argString)
    return undefined;
  const args = {};
  const argArray = argString.split('&');
  for (const arg of argArray) {
    const [ key, val ] = arg.split('=');
    if(val.match(','))
      args[key] = val.split(',');
    else
      args[key] = val;
  }
  return args;
}

export function decodeHash(hash) {
  const pageStart = hash.indexOf('#');
  const argsStart = hash.indexOf('?');
  const page = hash.substring(pageStart + 1, argsStart);
  const argString = argsStart >= 0 ? hash.substring(argsStart + 1, hash.length) : ''; 
  return { page: page, args: decodeArgs(argString) };
}