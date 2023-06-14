import { JSX } from "preact/jsx-runtime";
import { Input, Select } from "$this/components/Input.tsx";
import { Form } from "$this/components/Form.tsx";
import DateChecker from "$this/islands/DateChecker.tsx";
import { editMode, Mode, propMap, toISODateString } from "$this/common/util.ts";
import {
  CatRaceValues,
  EnvironmentValues,
  FurColorValues,
  ObjectOfInsurance,
  PersonalityValues,
  Policy,
} from "$this/generated/index.ts";

export interface EditPolicyProps extends JSX.HTMLAttributes<HTMLFormElement> {
  mode?: Mode;
  allrequired?: boolean;
  values?: Record<string, unknown>;
  customerId?: string;
}

const policy = propMap(new Policy());
const cat = propMap(new ObjectOfInsurance(), "objectOfInsurance.");
const today = toISODateString(new Date());
const getEditable = editMode([cat.personality, policy.coverage]);

export function EditPolicy(props: EditPolicyProps) {
  const { mode, customerId } = props;
  delete props.mode;
  delete props.customerId;
  return (
    <Form {...props} editable={getEditable(mode)}>
      <input name="customerId" type="hidden" value={customerId} />
      <Input
        name={policy.startDate}
        labeltext="Vertragsbeginn"
        type="date"
        min={today}
      />
      <Input
        name={policy.endDate}
        labeltext="Vertragsende"
        type="date"
        min={today}
      />
      <Input
        name={policy.dueDate}
        labeltext="Fälligkeit"
        type="date"
        min={today}
      />
      <DateChecker
        start={policy.startDate}
        end={policy.endDate}
        due={policy.dueDate}
      />
      <Input
        name={policy.coverage}
        labeltext="Jährliche Deckung"
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
      <div class="divider md:col-span-2">Katze</div>
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
        max={today}
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
        step="0.01"
        unit="kg"
      />
      {props.children}
    </Form>
  );
}
