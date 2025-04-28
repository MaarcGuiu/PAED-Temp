package Fase4.UI;

import Fase4.Taules.Production;
import Fase4.Taules.ProductionTable;
import Fase4.Model.ProductionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProductionUI {

    public static void addProduction(Scanner input, ProductionTable table) {
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
        table.addProduction(prod);

        System.out.println("\nEl " + type.getDisplayName() + " \"" + name + "\" s'ha registrat!\n");
        System.out.println("Totes les produccions registrades:");
        for (Production p : table.getAllProductions()) {
            System.out.printf(" - %s (%s) : %d€\n",
                    p.getName(),
                    p.getType().getDisplayName(),
                    p.getRevenue()
            );
        }
    }

    public static void removeProduction(Scanner input, ProductionTable table) {
        String aux = input.nextLine();

        System.out.print("Introdueix el nom de la producció a eliminar: ");
        String name = input.nextLine().trim();
        Production prod = table.findProductionByName(name);
        if (prod != null) {
            table.removeProduction(name);
            String typeName = prod.getType().name();
            int rev = prod.getRevenue();
            System.out.println("\nLa producció " + name + " (" + typeName + " - " + rev + "€) ha estat eliminada del sistema!\n");
        } else {
            System.out.println("\nNo existeix cap producció amb nom: " + name + "\n");
        }
    }

    public static void consultProduction(Scanner input, ProductionTable table) {
        String aux = input.nextLine();

        System.out.print("Introdueix el nom de la producció a consultar: ");
        String name = input.nextLine().trim();
        Production prod = table.findProductionByName(name);
        if (prod != null) {
            String typeName = prod.getType().name();
            int rev = prod.getRevenue();
            System.out.println("\nS'ha trobat la següent producció: " + name + " (" + typeName + " - " + rev + "€)\n");
        } else {
            System.out.println("\nNo s'ha trobat cap producció amb nom: " + name + "\n");
        }
    }

    public static void searchByRevenue(Scanner input, ProductionTable table) {
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
        for (Production p : table.getAllProductions()) {
            if (p.getRevenue() >= minRev && p.getRevenue() <= maxRev) {
                results.add(p);
            }
        }

        for (int i = 0; i < results.size() - 1; i++) {
            for (int j = 0; j < results.size() - 1 - i; j++) {
                if (results.get(j).getRevenue() < results.get(j + 1).getRevenue()) {
                    Production tmp = results.get(j);
                    results.set(j, results.get(j + 1));
                    results.set(j + 1, tmp);
                }
            }
        }

        System.out.println("\nLes produccions en el rang són:");
        for (Production p : results) {
            System.out.println(" * " + p.getName() + " (" + p.getType().name() + " - " + p.getRevenue() + "€)\n");
        }
    }

    public static void statistics(Scanner input, ProductionTable table) {
        String aux = input.nextLine();
        // TODO: implementar estadístiques
    }
}