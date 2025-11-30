# Contribuindo para o Off3D Studio – API Backend

Obrigado por querer contribuir!  
Siga estas diretrizes para manter o repositório organizado e profissional.

---

## 1️⃣ Como contribuir

- Reportar bugs
- Sugerir melhorias
- Implementar novas funcionalidades
- Melhorar testes ou documentação
- Revisar PRs

---

## 2️⃣ Reportando Issues

- Use um título claro e objetivo
- Descreva o problema ou a sugestão
- Inclua passos para reproduzir, se for bug
- Inclua logs, prints ou outputs relevantes
- Informe ambiente (SO, Java 21, versão do projeto)

---

## 3️⃣ Fluxo de Pull Request (PR)

1. Crie uma branch a partir da `main`:
```
   git checkout main
   git pull
   git checkout -b feature/nome-da-feature
```

2. Desenvolva sua alteração
3. Garanta que os testes passam (`mvn clean test`)
4. Abra um PR apontando para a `main`
5. Solicite revisão e aguarde aprovação

---

## 4️⃣ Padrão de Branches

2. Desenvolva sua alteração
3. Garanta que os testes passam (`mvn clean test`)
4. Abra um PR apontando para a `main`
5. Solicite revisão e aguarde aprovação

---

## 4️⃣ Padrão de Branches
```
tipo/nome-descritivo
```

Exemplos:
```
feature/cadastro-usuario
fix/ajuste-query-metrics
docs/atualiza-readme
tests/integration-metrics
chore/update-dependencies
```

---

## 5️⃣ Padrão de Commits (Conventional Commits)

Formato:

<tipo>(escopo opcional): descrição curta

Tipos comuns:

- `feat` → nova funcionalidade
- `fix` → correção de bug
- `docs` → documentação
- `style` → formatação
- `refactor` → refatoração
- `test` → testes
- `chore` → build/devops/dependências

Exemplos:
```
feat(metrics): adiciona endpoint de métricas mensais
fix(repository): corrige retorno nulo da query
docs(readme): adiciona instruções de execução
refactor(service): simplifica lógica de cálculo
test(dto): adiciona teste para MetricsDTO
chore(ci): configura GitHub Actions
```
---

## 6️⃣ Checklist antes de criar PR

- [ ] Código compila
- [ ] Testes passam
- [ ] Commit segue padrão
- [ ] PR descreve mudanças claramente
- [ ] Documentação atualizada se necessário

Obrigado por contribuir! 💙
