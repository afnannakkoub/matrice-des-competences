import { Role } from './role';

export interface Employee {

  id?: number;

  nom: string;

  prenom: string;

  email: string;

  poste: string;

  departement: string;

  actif: boolean;

  role: Role;

}