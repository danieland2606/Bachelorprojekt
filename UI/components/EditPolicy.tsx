import { JSX } from "preact/jsx-runtime";
import { Input, Select } from "$this/components/Input.tsx";
import { Form } from "$this/components/Form.tsx";
import { Obj, propMap } from "$this/common/util.ts";
import {
  CatRaceValues,
  EnvironmentValues,
  FurColorValues,
  ObjectOfInsurance,
  PersonalityValues,
  Policy,
} from "$this/generated/index.ts";

const policy = propMap(new Policy());
const cat = propMap(new ObjectOfInsurance(), "objectOfInsurance.");
const yearStart = new Date(new Date().getFullYear(), 0, 1).toISOString();
const yearEnd = new Date(new Date().getFullYear(), 11, 31).toISOString();

type Mode = "create" | "display" | "edit";

export interface EditPolicyProps extends JSX.HTMLAttributes<HTMLFormElement> {
  mode?: Mode;
  allrequired?: boolean;
  values?: Obj;
  customerId?: string;
}

function getEditable(mode?: Mode) {
  switch (mode) {
    case "display":
      return [];
    case "edit":
      return [
        cat.castrated,
        cat.personality,
        cat.environment,
        cat.weight,
        policy.coverage,
      ]; //TODO check Capgemini requirements
    case "create":
    default:
      return undefined;
  }
}

export function EditPolicy(props: EditPolicyProps) {
  const { mode, customerId } = props;
  delete props.mode;
  delete props.customerId;
  return (
    <Form {...props} editable={getEditable(mode)}>
      <input name="customerId" type="hidden" value={customerId} />
      <Input name={policy.startDate} labeltext="Begin" type="date" />
      <Input name={policy.endDate} labeltext="Ende" type="date" />
      {/*<Input name={policy.dueDate} labeltext="Fälligkeit" type="date" min={yearStart} max={yearEnd}></Input>*/}
      <Input
        name={policy.coverage}
        labeltext="Deckung"
        type="number"
        min="0"
        step="0.01"
        unit="€"
      />
      {mode !== "create" && (
        <Input
          name={policy.premium}
          labeltext="Rate"
          type="number"
          min="0"
          step="0.01"
          unit="€"
        />
      )}
      <div class="divider lg:col-span-2">Katze</div>
      <Input
        name={cat.name}
        labeltext="Name"
        type="text"
      />
      <Select
        name={cat.race}
        labeltext="Rasse"
        options={CatRaceValues}
      />
      <Select
        name={cat.color}
        labeltext="Farbe"
        options={FurColorValues}
      />
      <Input
        name={cat.dateOfBirth}
        labeltext="Geburtsdatum"
        type="date"
      />
      <Select name={cat.castrated} labeltext="Kastriert">
        <option value={true.toString()}>Kastriert</option>
        <option value={false.toString()}>Nicht Kastriert</option>
      </Select>
      <Select
        name={cat.personality}
        labeltext="Persönlichkeit"
        options={PersonalityValues}
      />
      <Select
        name={cat.environment}
        labeltext="Umgebung"
        options={EnvironmentValues}
      />
      <Input
        name={cat.weight}
        labeltext="Gewicht"
        type="number"
        min="0"
        step="0.1"
        unit="kg"
      />
      {props.children}
    </Form>
  );
}
