import { JSX } from "preact/jsx-runtime";
import { Input, Select } from "$this/components/Input.tsx";
import { Form } from "$this/components/Form.tsx";
import { editMode, Mode, Obj, propMap } from "$this/common/util.ts";
import {
  Address,
  Customer,
  EmploymentStatusValues,
  FormOfAddressValues,
  MaritalStatusValues,
  TitleValues,
} from "$this/generated/index.ts";

interface EditCustomerProps extends JSX.HTMLAttributes<HTMLFormElement> {
  mode?: Mode;
  allrequired?: boolean;
  values?: Obj;
}

const customer = propMap(new Customer());
const address = propMap(new Address(), "address.");
const getEditable = editMode([customer.employmentStatus]);

export function EditCustomer(props: EditCustomerProps) {
  return (
    <Form {...props} editable={getEditable(props.mode)}>
      <Select
        name={customer.formOfAddress}
        labeltext="Anrede"
        options={FormOfAddressValues}
      />
      <Select
        name={customer.title}
        labeltext="Titel"
        options={TitleValues}
      />
      <Input
        name={customer.firstName}
        labeltext="Vorname"
        type="text"
      />
      <Input
        name={customer.lastName}
        labeltext="Nachname"
        type="text"
      />
      <Input
        name={customer.phoneNumber}
        labeltext="Rufnummer"
        type="tel"
      />
      <Input
        name={customer.email}
        labeltext="E-mail"
        type="email"
      />
      <Input
        name={customer.dateOfBirth}
        labeltext="Geburtsdatum"
        type="date"
      />
      <Select
        name={customer.maritalStatus}
        labeltext="Familienstatus"
        options={MaritalStatusValues}
      />
      <Select
        name={customer.dogOwner}
        labeltext="Hundebesitzer"
      >
        <option value={true.toString()}>Besitzt einen Hund</option>
        <option value={false.toString()}>Besitzt keinen Hund</option>
      </Select>
      <Select
        name={customer.employmentStatus}
        labeltext="Berufsstand"
        options={EmploymentStatusValues}
      />
      <Input
        name={address.city}
        labeltext="Wohnort"
        type="text"
      />
      <Input
        name={address.street}
        labeltext="Straße"
        type="text"
      />
      <Input
        name={address.postalCode}
        labeltext="Postleitzahl"
        type="text"
        pattern={/\d{5}/.source}
      />
      <Input
        name={customer.bankDetails}
        labeltext="IBAN"
        type="text"
        pattern={/^[a-zA-Z]{2}\d{13,32}$/.source}
      />
    </Form>
  );
}
