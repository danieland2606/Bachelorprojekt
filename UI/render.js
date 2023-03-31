import {fetchCustomerList} from "./query.js";
import {documentReady} from "./util.js";

//TODO this doesn't belong here
window.fetchCustomerList = fetchCustomerList;

Element.prototype.getId = function() { return this.getAttribute('id'); };
Map.prototype.setIfDef = function (key, val) { if(key && val) this.set(key,val); };

await documentReady();

const templateContainers = getTemplateConainers();
const templates = getTemplates();

initialRender();

//////////////////////////////////////////////////////////////////////////////////

function bootstrap() {
  const topNode = document.querySelector('[controller]');
  const controller = topNode.getAttribute('controller');
  
}

function initialRender() {
  templateContainers.forEach(element => {
    const defaultTemplate = element.getAttribute('template-container')
    if(defaultTemplate)
      element.append(templates.get(defaultTemplate).content);
  });
  getTables().forEach((template, binding) => renderTable(template, binding));
}

async function renderTable(template, binding) {
  const providerFunc = binding.getAttribute('bind-array');
  const values = await window[providerFunc]();
  const nodes = values.map(val => renderRow(val, template.content.firstElementChild));
  binding.append(...nodes);          
}

function renderRow(data, template) {
  const row = template.cloneNode(true);
  const regex = /{{([^}]+)}}/g;
  row.innerHTML = row.innerHTML.replace(regex, match => fillPlaceholder(match, data));
  return row;
}

function fillPlaceholder(placeholder, values) {
  const property = placeholder.slice(2, -2).split('\.');
  return getProperty(property, values);
}

function getProperty(property, obj) {
  if(property.length === 1)
    return obj[property[0]];
  else
    return getProperty(property.slice(1), obj[property[0]]);  
}

function getTemplateConainers() {
  const containerMap = new Map();
  const containers = document.querySelectorAll('[template-container]');
  containers.forEach(element => containerMap.set(element.getId(), element));
  return containerMap;
}

function getTemplates() {
  const templateMap = new Map();
  const temps = document.querySelectorAll('template');
  temps.forEach(element => templateMap.setIfDef(element.getId(), element));
  return templateMap;
}

function getTables() {
  const boundTemplates = new Map();
  const bindings = document.querySelectorAll('[bind-array]')
  bindings.forEach(element => boundTemplates.setIfDef(element, element.querySelector('template')));
  return boundTemplates;
}
