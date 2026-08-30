import { Category } from '../../categories/models/category';

export interface Competence {

  id?: number;

  nom: string;

  description: string;

  archive: boolean;

  categorie: Category;

}