import { Input, Select } from "./Input.tsx";
import { Form, FormProps } from "./Form.tsx";

export function EditPolicy(props: FormProps) {
  return (
    <Form {...props}>
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
        <Select name="objectOfInsurance.race" labeltext="Rasse">
          <option value="siamese">Siamese</option>
          <option value="perser">Perser</option>
          <option value="bengal">Bengal</option>
          <option value="maine-coon">Maine Coon</option>
          <option value="sphynx">Sphynx</option>
          <option value="scottish-fold">Scottish Fold</option>
          <option value="british-shorthair">British Shorthair</option>
          <option value="abyssinian">Abyssinian</option>
          <option value="ragdoll">Ragdoll</option>
        </Select>
        <Select name="objectOfInsurance.color" labeltext="Farbe">
          <option value="seal">Seal</option>
          <option value="blau">Blau</option>
          <option value="lilac">Lilac</option>
          <option value="creme">Creme</option>
          <option value="weiss">Weiß</option>
          <option value="schildpatt">Schildpatt</option>
          <option value="schwarz">Schwarz</option>
          <option value="braun">Braun</option>
          <option value="marmor">Marmor</option>
          <option value="grau">Grau</option>
          <option value="rot">Rot</option>
          <option value="zimt">Zimt</option>
        </Select>
        <Input
          type="date"
          name="objectOfInsurance.dateOfBirth"
          labeltext="Geburtsdatum"
        >
        </Input>
        <Select name="objectOfInsurance.castrated" labeltext="Kastriert">
          <option value="true">Kastriert</option>
          <option value="false">Nicht Kastriert</option>
        </Select>
        <Select name="objectOfInsurance.personality" labeltext="Persönlichkeit">
          <option value="anhaenglich">Anhänglich</option>
          <option value="spielerisch">Spielerisch</option>
        </Select>
        <Select name="objectOfInsurance.environment" labeltext="Umgebung">
          <option value="draussen">Hauskatze</option>
          <option value="drinnen">Freigänger</option>
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
