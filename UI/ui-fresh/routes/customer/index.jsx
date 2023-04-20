import { Input, Select } from "../../components/Input.tsx";

export const handler = {
  async GET(_, ctx) {
    return await ctx.render();
  },
  async POST(req, ctx) {
    const form = await req.formData();
    for (const [key, val] of form.entries()) {
      console.debug(`key: ${key}, val: ${val}`);
    }
    const base = new URL(req.url).origin;
    return Response.redirect(new URL("/", base), 303);
  },
};

export default function CreateCustomer() {
  return (
    <>
      <h1>Neuen Kunden anlegen</h1>
      <form
        action="/customer"
        method="post"
        id="new-customer"
        class="box-column"
      >
        <Select name="formOfAddress" labeltext="Anrede">
          <option value="herr">Herr</option>
          <option value="frau">Frau</option>
        </Select>
        <Select name="title" labeltext="Anrede">
          <option value="doctor">Dr.</option>
          <option value="professor">Prof.</option>
        </Select>
        <Input type="text" name="firstName" labeltext="Vorname"></Input>
        <Input type="text" name="lastName" labeltext="Nachname"></Input>
        <Input type="tel" name="phoneNumber" labeltext="Rufnummer"></Input>
        <Input type="email" name="email" labeltext="E-mail"></Input>
        <Input type="date" name="dateOfBirth" labeltext="Geburtsdatum"></Input>
        <Select name="maritalStatus" labeltext="Familienstatus">
          <option value="ledig">Ledig</option>
          <option value="verheiratet">Verheiratet</option>
          <option value="verwitwet">Verwitwet</option>
        </Select>
        <Select name="employmentStatus" labeltext="Berufsstand">
          <option value="selbststaendig">Selbstständig</option>
          <option value="angestellt">Angestellt</option>
          <option value="arbeitslos">Arbeitslos</option>
          <option value="arbeitssuchend">Arbeitssuchend</option>
          <option value="ausbildung">In Ausbildung</option>
        </Select>
        <Input type="text" name="address.city" labeltext="Wohnort"></Input>
        <Input type="text" name="address.street" labeltext="Straße"></Input>
        <Input
          type="text"
          pattern="\d*"
          name="address.postalCode"
          labeltext="Postleitzahl"
        >
        </Input>
        <Input type="text" name="bankDetails" labeltext="IBAN"></Input>
      </form>
      <div class="box-row buttons">
        <a class="button" href="/">Abbrechen</a>
        <input
          form="new-customer"
          type="submit"
          class="button"
          value="Anlegen"
        >
        </input>
      </div>
    </>
  );
}
