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
      <input name="customerId" type="hidden" value={customerId}></input>
      <Input name={policy.startDate} labeltext="Begin" type="date"></Input>
      <Input name={policy.endDate} labeltext="Ende" type="date"></Input>
      <Input name={policy.coverage} labeltext="Deckung" type="number"></Input>
      {mode !== "create" && (
        <Input name={policy.premium} labeltext="Rate" type="number"></Input>
      )}
      <div class="box-column">
        <h2>Katze</h2>
        <Input
          name={cat.name}
          labeltext="Name"
          type="text"
        >
        </Input>
        <Select
          name={cat.race}
          labeltext="Rasse"
          options={CatRaceValues}
        >
        </Select>
        <Select
          name={cat.color}
          labeltext="Farbe"
          options={FurColorValues}
        >
        </Select>
        <Input
          name={cat.dateOfBirth}
          labeltext="Geburtsdatum"
          type="date"
        >
        </Input>
        <Select name={cat.castrated} labeltext="Kastriert">
          <option value={true.toString()}>Kastriert</option>
          <option value={false.toString()}>Nicht Kastriert</option>
        </Select>
        <Select
          name={cat.personality}
          labeltext="Persönlichkeit"
          options={PersonalityValues}
        >
        </Select>
        <Select
          name={cat.environment}
          labeltext="Umgebung"
          options={EnvironmentValues}
        >
        </Select>
        <Input
          name={cat.weight}
          labeltext="Gewicht"
          type="number"
        >
        </Input>
      </div>
      {props.children}
    </Form>
  );
}
