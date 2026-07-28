export interface User {

  id: number;

  nom: string;

  prenom: string;

  email: string;

  poste: string;

  departement: string;

  actif: boolean;

  role: {
    id: number;
    nom: string;
  };

  manager?: {
    id: number;
    nom: string;
    prenom: string;
  };

}