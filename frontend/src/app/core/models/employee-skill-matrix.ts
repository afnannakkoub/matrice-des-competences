


export interface EmployeeSkillMatrix {

  competenceId: number;

  competence: string;

  niveauRequis: number;

  niveauActuel: number | null;

  niveauEvaluation: number | null;

  gap: number | null;

  statut: string;
}