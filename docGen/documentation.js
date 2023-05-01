import { parse } from "yaml";
import widdershins from "widdershins";

let options = {};
options.codeSamples = true;
options.httpsnippet = false;
options.language_tabs = [
  { "http": "HTTP" },
  { "shell": "SHELL" },
  { "javascript": "JavaScript" },
];
options.language_clients = [{ "javascript": "fetch" }];
options.omitBody = true;
options.theme = "darkula";
options.search = true;
options.sample = true; // set false by --raw
options.discovery = false;
options.includes = [];
options.shallowSchemas = false; //true is buggy
options.tocSummary = false;
options.headings = 2;
options.yaml = false;
options.user_templates = "./templates";

options.templateCallback = function (_1, _2, data) {
  const schemas = data.components.schemas;
  for (const schemaName in schemas) {
    const schema = schemas[schemaName];
    for (const prop in schema) {
      if (prop == "allOf") {
        mergeProperties(schema, data);
        delete schema.allOf;
      }
    }
  }
  return data;
};

const yaml = await Deno.readTextFile("../Meowmed+_REST.yaml");
const api = parse(yaml);
try {
  const doc = await widdershins.convert(api, options);
  Deno.writeTextFile("../Meowmed+_REST.md", doc);
} catch (err) {
  console.error(err);
}

function mergeProperties(object, data) {
  for (const obj of object.allOf) {
    for (const prop in obj) {
      if (prop === "$ref") {
        Object.assign(obj, structuredClone(resolveRef(obj.$ref, data)));
        delete obj.$ref;
      }
    }
    deepAssign(object, obj);
  }
}

function deepAssign(target, source) {
  if (!target || !source || !isObject(target) || !isObject(source)) {
    return;
  }
  for (const prop in source) {
    if (!target.hasOwnProperty(prop)) {
      target[prop] = source[prop];
    } else {
      deepAssign(target[prop], source[prop]);
    }
  }
}

function resolveRef(ref, data) {
  const props = ref.split("/").slice(1);
  return props.reduce(function (prev, cur) {
    return prev ? prev[cur] : null;
  }, data);
}

function isObject(item) {
  return (item && typeof item === "object" && !Array.isArray(item));
}
