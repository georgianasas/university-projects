#include "ui.h"
#include <iomanip>
#include"service.h"
#include<iostream>
#include<vector>
#include"exceptii.h"
using namespace std;

UI::UI(Service&service):service(service) {}

void UI::afiseaza_meniu() {
    cout<<"MENIU"<<endl;
    cout<<"1.Adauga produs:"<<endl;
    cout<<"2.Afisare produse:"<<endl;
    cout<<"3.Sterge produs:"<<endl;
    cout<<"4.Modifica produs:"<<endl;
    cout<<"5.Cumpara produs:"<<endl;
    cout<<"6.Afiseaza monede disponibile in tonomat:"<<endl;
    cout<<"7.Adauga monede in tonomat:"<<endl;
    cout<<"8.Afiseaza suma totala in tonomat:"<<endl;
    cout<<"0.Iesire!"<<endl;
}

void UI::afisare_toate() {
    vector<Produs> produse;
    produse=service.get_all();
    for (int i=0;i<produse.size();i++) {
        cout<<"Cod: "<<produse[i].get_cod()<<endl;
        cout<<"Nume: "<<produse[i].get_nume()<<endl;
        cout<<"Pret: " <<produse[i].get_pret()<<endl;

    }
}

void UI::run() {
    int op,c;
    string n;
    double p;
    while (true) {
        afiseaza_meniu();
        cout<<"Alege optiune: "<<endl;
        cin>>op;
        try{
        if (op==1) {
            cout<<"Cod produs: ";
            cin>>c;
            cin.ignore();
            cout<<"Nume produs:";
            getline(cin,n);
            cout<<"Pret produs:";
            cin>>p;
            service.adauga_produs(c,n,p);
            cout<<"Produsul a fost adaugat cu succes!\n";
        }
        else if (op==2) {
            afisare_toate();
        }
        else if (op==3) {
            cout<<"Cod produs:"<<endl;
            cin>>c;
            service.deleteItem(c);
            cout<<"Produsul a fost sters cu succes!\n";
        }
        else if (op==4) {
            cout<<"Cod produs:"<<endl;
            cin>>c;
            cout<<"Nume nou  produs:"<<endl;
            cin>>n;
            cout<<"Pret nou produs:"<<endl;
            cin>>p;
            service.updateItem(c,n,p);
            cout<<"Produsul a fost actualizat cu succes!\n";
        }
        else if (op==5) {
            cout<<"Cod produs: "<<endl;
            cin>>c;
            cout<<"Suma introdusa: "<<endl;
            cin>>p;
            string rezultat=service.achizitieProdus(c,p);
            cout<<"Rezultat: "<<rezultat<<endl;
        }
        else if (op==6) {
            cout<<service.getMonedeDisponibile()<<endl;
        }
        else if (op==7) {
            cout<<"Numarul de monede care se vor adauga este:"<<endl;
            int nr;
            cin>>nr;
            vector<double>monede;
            for (int i=0;i<nr;i++) {
                cout<<"Moneda"<<(i+1)<<":";
                double m;
                cin>>m;
                monede.push_back(m);
            }
            try {
                service.adaugaMonedeTonomat(monede);
                cout<<"Monede adaugate cu succes!\n";
            }catch (const ExceptieTonomat& e) {
                cout<<"Eroare: "<<e.what()<<endl;
            }
        }
        else if (op==8) {
            double suma=service.getSumaTotalaInTonomat();
            cout<<fixed<<setprecision(2);
            cout<<"Suma totala disponibila in tonomat: "<<suma<<"lei\n";
        }
        else if (op==0) {
            cout<<"La revedere!";
            return ;
        }
        else {
            cout<<"Optiune invalida!\n";
        }
    }catch (const ExceptieProdus& e) {
        cout<<"Eroare Produs: "<<e.what()<<endl;

    }catch (const ExceptieRepo& e) {
        cout<<"Eroare Repo: "<<e.what()<<endl;

    } catch (const ExceptieTonomat& e) {
        cout<<"Eroare Tonomat: "<<e.what()<<endl;

    }catch (const std::exception& e) {
        cout<<"Eroare necunoscuta: "<<e.what()<<endl;
    }
}
}