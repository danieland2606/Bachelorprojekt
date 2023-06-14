import { useEffect } from "preact/hooks";

export interface InputIDs {
  start: string;
  end: string;
  due: string;
}

export default function DateChecker(props: InputIDs) {
  useEffect(() => {
    const inputs = getElements(props, document);
    if (!allFound(inputs)) {
      return;
    }
    const validator = makeValidator(inputs);
    inputs.forEach((input) => input.addEventListener("input", validator));
    return () => {
      inputs.forEach((input) => input.removeEventListener("input", validator));
    };
  });
  return <div class="hidden" />; // Islands must return an element.
}

function makeValidator(inputs: HTMLInputElement[]) {
  const [startInput, endInput, dueInput] = inputs;
  return (_: Event) => {
    const [startDate, endDate, dueDate] = getDates(inputs);
    const startPlus2Months = getDatePlus2Months(startDate);
    const hints: string[][] = [[], [], []];
    const [startHint, endHint, dueHint] = hints;
    if (greater(startDate, endDate)) {
      startHint.push("Beginn muss vor Ende des Vertrags liegen.");
      endHint.push("Ende muss nach Beginn des Vertrages liegen.");
    }
    if (greater(startDate, dueDate)) {
      startHint.push("Beginn muss vor Fälligkeit liegen.");
      dueHint.push("Fälligkeit muss nach Beginn des Vertrages liegen.");
    }
    if (greater(dueDate, endDate)) {
      dueHint.push("Fälligkeit muss vor Ende des Vertrags liegen.");
      endHint.push("Ende muss nach Fälligkeit liegen.");
    }
    if (greater(dueDate, startPlus2Months)) {
      dueHint.push(
        "Fälligkeit darf nicht länger als zwei Monate nach Beginn des Vertrags liegen.",
      );
    }
    hints.forEach((hint, i) => inputs[i].setCustomValidity(hint.join(" ")));
  };
}

function getDatePlus2Months(date: Date | null) {
  return date != null
    ? new Date(date.getFullYear(), date.getMonth() + 2, date.getDay()) // Date constructor will roll over Month > 11
    : null;
}

function getElements({ start, end, due }: InputIDs, doc: Document) {
  return [getInput(start), getInput(end), getInput(due)];

  function getInput(id: string) {
    return doc.querySelector(`input#${id}`) as
      | HTMLInputElement
      | null
      | undefined;
  }
}

function getDates(inputs: HTMLInputElement[]) {
  return inputs.map((element) => element.valueAsDate);
}

function greater(d1: Date | null, d2: Date | null) {
  return d1 != null && d2 != null && d1 > d2;
}

function allFound(
  inputs: (HTMLInputElement | null | undefined)[],
): inputs is HTMLInputElement[] {
  return !inputs.some((input) => input == null);
}
