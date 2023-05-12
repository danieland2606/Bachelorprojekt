import { Input, Select } from "$this/components/Input.tsx";
import { Form, FormProps } from "$this/components/Form.tsx";
import {
  Customer,
  EmploymentStatusValues,
  FormOfAddressValues,
  MaritalStatusValues,
  TitleValues,
} from "../generated/index.ts";

type proptype = {
  name: string;
  baseName: string;
  type: string;
  format: string;
};

const customerProps = function () {
  const props = new Map<string, proptype>();
  //TODO
  return props;
}();

export function EditCustomer(props: FormProps) {
  return (
    <Form {...props}>
      <Select
        name="formOfAddress"
        labeltext="Anrede"
        options={FormOfAddressValues}
      >
      </Select>
      <Select name="title" labeltext="Titel" options={TitleValues}>
      </Select>
      <Input type="text" name="firstName" labeltext="Vorname">
      </Input>
      <Input type="text" name="lastName" labeltext="Nachname">
      </Input>
      <Input
        type="tel"
        name="phoneNumber"
        labeltext="Rufnummer"
      >
      </Input>
      <Input type="email" name="email" labeltext="E-mail">
      </Input>
      <Input
        type="date"
        name="dateOfBirth"
        labeltext="Geburtsdatum"
      >
      </Input>
      <Select
        name="maritalStatus"
        labeltext="Familienstatus"
        options={MaritalStatusValues}
      >
      </Select>
      <Select name="dogOwner" labeltext="Hundebesitzer">
        <option value={true.toString()}>Besitzt einen Hund</option>
        <option value={false.toString()}>Besitzt keinen Hund</option>
      </Select>
      <Select
        name="employmentStatus"
        labeltext="Berufsstand"
        options={EmploymentStatusValues}
      >
      </Select>
      <Input
        type="text"
        name="address.city"
        labeltext="Wohnort"
      >
      </Input>
      <Input
        type="text"
        name="address.street"
        labeltext="Straße"
      >
      </Input>
      <Input
        type="text"
        pattern="\d*"
        name="address.postalCode"
        labeltext="Postleitzahl"
      >
      </Input>
      <Input type="text" name="bankDetails" labeltext="IBAN">
      </Input>
    </Form>
  );
}
