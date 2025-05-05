package Fase4.UI;

import Fase4.Taules.HashMap;
import Fase4.Taules.Production;
import Fase4.Model.ProductionType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class ProductionUI {

    public static void addProduction(Scanner input, HashMap table) {
        String aux = input.nextLine();

        System.out.print("Nom de la producció: ");
        String name = input.nextLine().trim();

        ProductionType type = null;
        while (type == null) {
            System.out.print("Tipus de producció: ");
            String typeText = input.nextLine().trim();
            for (ProductionType pt : ProductionType.values()) {
                if (pt.name().equalsIgnoreCase(typeText)
                        || pt.getDisplayName().equalsIgnoreCase(typeText)) {
                    type = pt;
                    break;
                }
            }
            if (type == null) {
                System.out.println("\nTipus no vàlid. Torna-ho a intentar.\n");
            }
        }

        int revenue;
        while (true) {
            System.out.print("Diners generats: ");
            String revLine = input.nextLine().trim();
            try {
                revenue = Integer.parseInt(revLine);
                if (revenue < 0) {
                    System.out.println("\nImport no vàlid. Ha de ser un enter >= 0.\n");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nImport no vàlid. Ha de ser un enter >= 0.\n");
            }
        }

        Production prod = new Production(name, type, revenue);
        table.put(prod.getName(), prod);

        HashMap.Entrada[] totes = table.getAllProductions();
        totes= table.getAllProductions();

        System.out.println("\nProducció afegida: " + name + " (" + type.name() + " - " + revenue + "€)\n");
        System.out.println("Produccions actuals:");
        for (int i = 0; i < totes.length; i++) {
            if (totes[i] != null && !totes[i].getIsFree()) {
                System.out.println(" * " + totes[i].getValue().getName() + " (" + totes[i].getValue().getType().name() + " - " + totes[i].getValue().getRevenue() + "€)\n");
            }
        }
    }

    public static void removeProduction(Scanner input, HashMap table) {
        String aux = input.nextLine();

        System.out.print("Introdueix el nom de la producció a eliminar: ");
        String name = input.nextLine().trim();
        Production prod = table.get(name);
        if (prod != null) {
            table.remove(name);
            String typeName = prod.getType().name();
            int rev = prod.getRevenue();
            System.out.println("\nLa producció " + name + " (" + typeName + " - " + rev + "€) ha estat eliminada del sistema!\n");
        } else {
            System.out.println("\nNo existeix cap producció amb nom: " + name + "\n");
        }
    }

    public static void consultProduction(Scanner input, HashMap table) {
        String aux = input.nextLine();

        System.out.print("Introdueix el nom de la producció a consultar: ");
        String name = input.nextLine().trim();
        Production prod = table.get(name);
        if (prod != null) {
            String typeName = prod.getType().name();
            int rev = prod.getRevenue();
            System.out.println("\nS'ha trobat la següent producció: " + name + " (" + typeName + " - " + rev + "€)\n");
        } else {
            System.out.println("\nNo s'ha trobat cap producció amb nom: " + name + "\n");
        }
    }

    public static void searchByRevenue(Scanner input, HashMap table) {
        String aux = input.nextLine();

        int minRev;
        while (true) {
            System.out.print("Introdueix el mínim de diners generats: ");
            if (input.hasNextInt()) {
                minRev = input.nextInt();
                input.nextLine();
                if (minRev >= 0) break;
            } else {
                input.nextLine();
            }
            System.out.println("\nValor no vàlid. Introdueix un enter.\n");
        }

        int maxRev;
        while (true) {
            System.out.print("Introdueix el màxim de diners generats: ");
            if (input.hasNextInt()) {
                maxRev = input.nextInt();
                input.nextLine();
                if (maxRev >= minRev) break;
            } else {
                input.nextLine();
            }
            System.out.println("\nValor no vàlid. Ha de ser >= mínim.\n");
        }

        List<Production> results = new ArrayList<>();
        for (HashMap.Entrada e : table.getAllProductions()) {
            if (e != null && !e.getIsFree()) {
                Production p = e.getValue();
                int rev = p.getRevenue();
                if (rev >= minRev && rev <= maxRev) {
                    results.add(p);
                }
            }
        }

        results.sort(Comparator.comparingInt(Production::getRevenue).reversed());

        if (results.isEmpty()) {
            System.out.println("\nNo s'ha trobat cap producció en el rang indicat.\n");
            return;
        }

        System.out.println("\nLes produccions en el rang són:");
        for (Production p : results) {
            System.out.println(" * " + p.getName() + " (" + p.getType().name() + " - " + p.getRevenue() + "€)\n");
        }
    }

    public static void statistics(Scanner input, HashMap table) {
        String aux = input.nextLine();
        // TODO: implementar estadístiques
    }
}