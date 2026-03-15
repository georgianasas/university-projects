#include "RepoFile.h"
#include <fstream>
#include <iostream>
#include <sstream>
#include "exceptii.h"
#include "produs.h"

RepoFile::RepoFile(const std::string& filename) : filename(filename) {
    loadFromFile();
}

void RepoFile::loadFromFile() {
    produse.clear();

    std::ifstream in(filename);
    if (!in.is_open()) {
        throw ExceptieRepo("Nu se poate deschide fisierul: " + filename);
    }

    int cod;
    std::string nume;
    double pret;

    while (in >> cod >> nume >> pret) {
        try {
            produse.push_back(Produs(cod, nume, pret));
        } catch (const ExceptieProdus& e) {
            std::cerr << "Produs invalid in fisier: cod=" << cod << ", nume=[" << nume << "], pret=" << pret << "\n";
            std::cerr << "Mesaj: " << e.what() << "\n";
        }
    }

    in.close();
}

void RepoFile::saveToFile() {
    std::ofstream out(filename);
    if (!out.is_open()) {
        throw ExceptieRepo("Nu se poate deschide fisierul: " + filename);
    }

    for (const auto& p : getAll()) {
        out << p.get_cod() << " " << p.get_nume() << " " << p.get_pret() << "\n";
    }

    out.close();
}

void RepoFile::addItem(const Produs& p) {
    Repo::addItem(p);
    saveToFile();
}

void RepoFile::deleteItem(int cod) {
    Repo::deleteItem(cod);
    saveToFile();
}

void RepoFile::updateItem(int cod, const Produs& p) {
    Repo::updateItem(cod, p);
    saveToFile();
}
