export interface SkillMatrix {

  utilisateurId: number;

  nom: string;

  prenom: string;

  poste?: string;

  departement?: string;

  competences: {
    [key: string]: number;
  };

}