#include <iostream>
#include "ATM.h"
#include "comparators.h"

using namespace std;

int main() {
    int optiuneSortare;
    Comparator<Tranzactie> comparator;

    cout << "Alege criteriul de sortare pentru tranzactii:\n";
    cout << "1. Dupa data\n";
    cout << "2. Dupa suma retrasa\n";
    cout << "3. Dupa numar de bancnote utilizate\n";
    cout << "Optiune: ";
    cin >> optiuneSortare;

    switch (optiuneSortare) {
        case 1:
            comparator = cmp_dupa_data;
            break;
        case 2:
            comparator = cmp_dupa_suma;
            break;
        case 3:
            comparator = cmp_dupa_bancnote;
            break;
        default:
            cout << "Optiune invalida. Se foloseste sortare dupa data.\n";
            comparator = cmp_dupa_data;
    }

    ATM atm(comparator);

    int optiune;
    do {
        cout << "\nMeniu:\n";
        cout << "1. Adauga bancnote\n";
        cout << "2. Retrage suma\n";
        cout << "3. Afiseaza bancnote\n";
        cout << "4. Afiseaza tranzactii sortate\n";
        cout << "5. Afisare tranzactii sortate dupa data\n";
        cout << "6. Afisare tranzactii sortate dupa suma retrasa\n";
        cout << "7. Afisare tranzactii sortate dupa numar de bancnote\n";
        cout << "8. Iesire\n";
        cout << "Optiune: ";
        cin >> optiune;

        switch (optiune) {
            case 1: {
                int val, nr;
                cout << "Valoare bancnota: ";
                cin >> val;
                cout << "Numar bancnote: ";
                cin >> nr;
                atm.adaugaBancnote(val, nr);
                break;
            }
            case 2: {
                int suma;
                cout << "Suma de retras: ";
                cin >> suma;
                atm.retrageSuma(suma);
                break;
            }
            case 3:
                atm.afisareBancnote();
                break;
            case 4:
                atm.afisareTranzactii();
                break;
            case 5:
                atm.afisareTranzactiiSortate(cmp_dupa_data);
            break;
            case 6:
                atm.afisareTranzactiiSortate(cmp_dupa_suma);
            break;
            case 7:
                atm.afisareTranzactiiSortate(cmp_dupa_bancnote);
            break;

            case 8:
                cout << "Program terminat!" << endl;
                break;
            default:
                cout << "Optiune invalida!" << endl;
        }

    } while (optiune != 8);

    return 0;
}