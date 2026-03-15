#ifndef REPOFILE_H
#define REPOFILE_H

#include "repo.h"
#include <string>

class RepoFile : public Repo {
private:
    std::string filename;

    void loadFromFile();
    void saveToFile();

public:
    RepoFile(const std::string& filename);

    void addItem(const Produs& p);
    void deleteItem(int cod);
    void updateItem(int cod, const Produs& p);
};

#endif//REPOFILE_H
