#ifndef SERVICE_H
#define SERVICE_H
#include"repo.h"
#include"produs.h"
#include"RepoFile.h"
#include"TonomatBani.h"
class Service {
private:
    RepoFile& repo;
    TonomatBani tonomatBani;
public:
    Service(RepoFile&r);

    void adauga_produs(int c,const string&n,double p);
    vector<Produs>get_all()const;
    int get_size()const;

    void deleteItem(int cod);
    void updateItem(int c,string n,double p);
    string achizitieProdus(int cod, double sumaClient);
    std::string getMonedeDisponibile() const;
    void adaugaMonedeTonomat(const std::vector<double>&monede);
    double getSumaTotalaInTonomat()const;
};
#endif //SERVICE_H