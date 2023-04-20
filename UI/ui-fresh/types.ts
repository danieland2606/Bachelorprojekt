export interface Customer {
  id: number | undefined;
  firstName: string | undefined;
  lastName: string | undefined;
  formOfAdress: string | undefined;
  title: string | undefined;
  maritalStatus: string | undefined;
  dateOfBirth: string | undefined;
  employmentStatus: string | undefined;
  address: Address | undefined;
  phoneNumber: string | undefined;
  email: string | undefined;
  bankDetails: string | undefined;
}

export interface Address {
  city: string | undefined;
  street: string | undefined;
  postalCode: number | undefined;
}
