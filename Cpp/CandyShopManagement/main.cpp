#include<iostream>
#include"teste.h"
#include"UI.h"
#include"RepoFile.h"
#include<filesystem>

int main() {
    try {
        ruleazaTeste();
        cout << "Teste rulate\n";

        cout << "Pas 1: Hello!" << endl;

        RepoFile repo("produse.txt");
        cout << "Pas 2: Repo initializat\n";

        Service service(repo);
        cout << "Pas 3: Service initializat\n";

        UI ui(service);
        cout << "Pas 4: UI initializat\n";

        ui.run();
    } catch (const std::exception &e) {
        std::cerr << "Eroare fatala:!" << e.what() << endl;
        return 1;
    }

    return 0;
}
