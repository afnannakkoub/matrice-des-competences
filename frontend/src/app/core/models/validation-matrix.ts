export interface ValidationMatrixCell {

  niveau: number;

  pourcentage: number;

}


export interface ValidationMatrixRow {

  poste: string;

  employes: {
    [employeeId: number]: ValidationMatrixCell;
  };

}