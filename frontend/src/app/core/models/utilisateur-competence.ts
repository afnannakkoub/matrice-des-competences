export interface UtilisateurCompetence {

  id?: number;

  utilisateur: any;

  competence: {
    id: number;
    nom: string;
  };

  niveauEmploye: number;

  niveauValide: number | null;

  statut: string;

  dateCreation?: string;

  dateValidation?: string;

  manager?: any;
}