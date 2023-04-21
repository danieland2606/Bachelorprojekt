export function capitalize(str: string) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

export function compareId({ id: id1 = 0 }, { id: id2 = 0 }) {
  return id1 - id2;
}
