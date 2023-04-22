export interface Customer {
  id?: number;
  firstName: string;
  lastName: string;
  formOfAdress: string;
  title: string;
  maritalStatus: string;
  dateOfBirth: string;
  employmentStatus: string;
  address: Address;
  phoneNumber: string;
  email: string;
  bankDetails: string;
}

export interface TableCustomer {
  id: number;
  firstName: string;
  lastName: string;
  address: Address;
}

export interface Address {
  city: string;
  street: string;
  postalCode: number;
}

export interface Policy {
  id?: number;
  startDate: string;
  endDate: string;
  coverage: number;
  premium: number;
  objectOfInsurance: ObjectOfInsurance;
}

export interface ObjectOfInsurance {
  name: string;
  race: string;
  color: string;
  dateOfBirth: string;
  castrated: string;
  personality: string;
  environment: string;
  weight: number;
}

export interface TablePolicy {
  id: number;
  startDate: string;
  endDate: string;
  coverage: number;
  objectOfInsurance: Cat;
}

export interface Cat {
  name: string;
}
