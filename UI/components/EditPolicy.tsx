import { Input, Select } from "./Input.tsx";
import { Form, FormProps } from "./Form.tsx";
import {
  CatRaceValues,
  EnvironmentValues,
  FurColorValues,
  PersonalityValues,
} from "../generated/index.ts";

export interface EditPolicyProps extends FormProps {
  customerId: string;
}

export function EditPolicy(props: EditPolicyProps) {
  return (
    <Form {...props}>
      <input type="hidden" name="customerId" value={props.customerId}></input>
      <Input type="date" name="startDate" labeltext="Begin"></Input>
      <Input type="date" name="endDate" labeltext="Ende"></Input>
      <Input type="number" name="coverage" labeltext="Deckung"></Input>
      {props.readonly && (
        <Input type="number" name="premium" labeltext="Rate"></Input>
      )}
      <div class="box-column">
        <h2>Katze</h2>
        <Input
          type="text"
          name="objectOfInsurance.name"
          labeltext="Name"
        >
        </Input>
        <Select
          name="objectOfInsurance.race"
          labeltext="Rasse"
          options={CatRaceValues}
        >
        </Select>
        <Select
          name="objectOfInsurance.color"
          labeltext="Farbe"
          options={FurColorValues}
        >
        </Select>
        <Input
          type="date"
          name="objectOfInsurance.dateOfBirth"
          labeltext="Geburtsdatum"
        >
        </Input>
        <Select name="objectOfInsurance.castrated" labeltext="Kastriert">
          <option value={true.toString()}>Kastriert</option>
          <option value={false.toString()}>Nicht Kastriert</option>
        </Select>
        <Select
          name="objectOfInsurance.personality"
          labeltext="Persönlichkeit"
          options={PersonalityValues}
        >
        </Select>
        <Select
          name="objectOfInsurance.environment"
          labeltext="Umgebung"
          options={EnvironmentValues}
        >
        </Select>
        <Input
          type="number"
          name="objectOfInsurance.weight"
          labeltext="Gewicht"
        >
        </Input>
      </div>
      {props.children}
    </Form>
  );
}
