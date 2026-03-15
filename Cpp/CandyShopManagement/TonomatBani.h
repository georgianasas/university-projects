#ifndef TONOMATBANI_H
#define TONOMATBANI_H

#include <vector>
#include <string>

class TonomatBani {
private:
    std::vector<double> monedeDisponibile;

public:
    TonomatBani();
    std::string achizitie(double pret, double sumaClient);
    void initMonede();
    std::string afiseazaMonedeDisponibile() const;
    void adaugaMonede(const std::vector<double>&monede);
    double getSumaTotala() const;
};

#endif//TONOMATBANI_H
