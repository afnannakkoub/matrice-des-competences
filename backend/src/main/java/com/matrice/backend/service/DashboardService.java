package com.matrice.backend.service;
import com.matrice.backend.DTO.DashboardDTO;
import com.matrice.backend.entity.Role;
import com.matrice.backend.entity.StatutEvaluation;
import com.matrice.backend.repository.CategorieRepository;
import com.matrice.backend.repository.CompetenceRepository;
import com.matrice.backend.repository.UtilisateurCompetenceRepository;
import com.matrice.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
@Service
public class DashboardService {
    private final UtilisateurRepository utilisateurRepository;
    private final CompetenceRepository competenceRepository;
    private final CategorieRepository categorieRepository;
    private final UtilisateurCompetenceRepository utilisateurCompetenceRepository;

    public DashboardService(
            UtilisateurRepository utilisateurRepository,
            CompetenceRepository competenceRepository,
            CategorieRepository categorieRepository,
            UtilisateurCompetenceRepository utilisateurCompetenceRepository) {

        this.utilisateurRepository = utilisateurRepository;
        this.competenceRepository = competenceRepository;
        this.categorieRepository = categorieRepository;
        this.utilisateurCompetenceRepository = utilisateurCompetenceRepository;
    }

    public DashboardDTO getDashboard() {

        DashboardDTO dto = new DashboardDTO();

        dto.setTotalUsers(
                utilisateurRepository.count()
        );

        dto.setTotalCompetences(
                competenceRepository.count()
        );

        dto.setTotalCategories(
                categorieRepository.count()
        );

        dto.setPendingValidations(
                utilisateurCompetenceRepository.countByStatut(
                        StatutEvaluation.EN_ATTENTE
                )
        );

        dto.setTotalManagers(
                utilisateurRepository.countByRoleNom("MANAGER")
        );

        dto.setTotalEmployees(
                utilisateurRepository.countByRoleNom("EMPLOYE")
        );

        return dto;
    }
}
