package transport;

import transport.core.*;
import java.time.*;

public class Test {
    public static void main(String[] args) {
        // créer le service de réclamation
        ServiceReclamation service = new ServiceReclamation();
        //créer 2 personnes, une station et un bus
        Usager usager1 = new Usager("Abdi", "Yasmine",  LocalDate.of(1990, 5, 20), true);
        Employe employe1 = new Employe("Alili", "Ahmed", LocalDate.of(1990, 5, 20), true, "EMP-1", TypeFonction.CONDUCTEUR);
        MoyenTransport bus1 = new MoyenTransport("BUS1");
        Station station1= new Station("Oued Smar");

        // 1.Créer des réclamations et les soumettre
        Reclamation r1 = new Reclamation(usager1, TypeReclamation.SERVICE,bus1,"Bus en retard", LocalDate.of(2025,1,11));
        service.soumettre(r1);
        Reclamation r2 = new Reclamation(employe1, TypeReclamation.TECHNIQUE,bus1,"Panne de moteur", LocalDate.of(2025,2,12));
        service.soumettre(r2);
        Reclamation r3 = new Reclamation(employe1, TypeReclamation.TECHNIQUE,station1,"lecteur magnétique en panne", LocalDate.of(2025,3,13));       
        service.soumettre(r3);
        Reclamation r4 = new Reclamation(usager1, TypeReclamation.SERVICE,station1,"Absence d'agent au guichet",LocalDate.of(2025,4,14));       
        service.soumettre(r4);
        Reclamation r5 = new Reclamation(usager1, TypeReclamation.SERVICE,bus1," Excès de vitesse",LocalDate.of(2025,5,15));
        service.soumettre(r5);
        
                          
        // Afficher  l’ensemble des réclamations
        System.out.println("***********     Liste de toutes les réclamations par Type   **********");
        service.afficherReclamations();
        
        // Afficher  l’ensemble des réclamations soumises par l'employé Ahmed Ali
        System.out.println("**********    Réclamations soumises par "+ employe1);
        service.afficherReclamations(employe1);
        
        // Afficher  l’ensemble des réclamations soumises par l'usager Yasmine Benkhalifa
        System.out.println("**********    Réclamations soumises par "+ usager1);
        service.afficherReclamations(usager1);
        
        // Afficher  l’ensemble des réclamations concernant le bus1
        System.out.println("**********    Réclamations concernant "+ bus1.toString() + " [Etat : " +bus1.getEtat()+ "]");
        service.afficherReclamations(bus1);
        
        // Afficher  l’ensemble des réclamations tconcernant la station1
        System.out.println("***********   Réclamations concernant "+ station1.toString() + " [Etat : " +station1.getEtat()+ "]");
        service.afficherReclamations(station1);
        
        // Résoudre une réclamation relative au BUS1 pour lever la suspension
        service.resoudre(r1);
                  
        // Vérifier que le bus a été récativé
        System.out.println("**********    Réclamations concernant "+ bus1.toString() + " [Etat : " +bus1.getEtat()+ "]");
        service.afficherReclamations(bus1);
    }
}
