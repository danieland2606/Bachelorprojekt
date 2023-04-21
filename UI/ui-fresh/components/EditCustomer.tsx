import { JSX } from "preact/jsx-runtime";
import { Input, Select } from "./Input.tsx";

interface CustomerProps extends JSX.HTMLAttributes<HTMLFormElement> {
  readonly?: boolean;
  allrequired?: boolean;
}

export function EditCustomer(props: CustomerProps) {
  const subProps = [];
  if (props.allrequired) {
    subProps.push("required");
  }
  if (props.readonly) {
    subProps.push("readonly");
  }
  return (
    <>
      <form
        {...props}
        action="/customer"
        method="post"
        class="box-column"
      >
        <Select {...subProps} name="formOfAddress" labeltext="Anrede">
          <option value="herr">Herr</option>
          <option value="frau">Frau</option>
        </Select>
        <Select {...subProps} name="title" labeltext="Anrede">
          <option value="doctor">Dr.</option>
          <option value="professor">Prof.</option>
        </Select>
        <Input {...subProps} type="text" name="firstName" labeltext="Vorname">
        </Input>
        <Input {...subProps} type="text" name="lastName" labeltext="Nachname">
        </Input>
        <Input
          {...subProps}
          type="tel"
          name="phoneNumber"
          labeltext="Rufnummer"
        >
        </Input>
        <Input {...subProps} type="email" name="email" labeltext="E-mail">
        </Input>
        <Input
          {...subProps}
          type="date"
          name="dateOfBirth"
          labeltext="Geburtsdatum"
        >
        </Input>
        <Select {...subProps} name="maritalStatus" labeltext="Familienstatus">
          <option value="ledig">Ledig</option>
          <option value="verheiratet">Verheiratet</option>
          <option value="verwitwet">Verwitwet</option>
        </Select>
        <Select {...subProps} name="employmentStatus" labeltext="Berufsstand">
          <option value="selbststaendig">Selbstständig</option>
          <option value="angestellt">Angestellt</option>
          <option value="arbeitslos">Arbeitslos</option>
          <option value="arbeitssuchend">Arbeitssuchend</option>
          <option value="ausbildung">In Ausbildung</option>
        </Select>
        <Input
          {...subProps}
          type="text"
          name="address.city"
          labeltext="Wohnort"
        >
        </Input>
        <Input
          {...subProps}
          type="text"
          name="address.street"
          labeltext="Straße"
        >
        </Input>
        <Input
          {...subProps}
          type="text"
          pattern="\d*"
          name="address.postalCode"
          labeltext="Postleitzahl"
        >
        </Input>
        <Input {...subProps} type="text" name="bankDetails" labeltext="IBAN">
        </Input>
      </form>
      {props.children}
    </>
  );
}
