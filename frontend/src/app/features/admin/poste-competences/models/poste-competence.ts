import { Competence } from '../../competences/models/competence';

export interface PosteCompetence {

  id?: number;

  poste: string;

  competence: Competence;

  niveauRequis: number;

}