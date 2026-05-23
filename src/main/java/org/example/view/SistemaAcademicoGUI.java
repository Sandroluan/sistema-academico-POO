package org.example.view;

import org.example.controller.AlunoController;
import org.example.controller.NotaFaltaController;
import org.example.model.Aluno;
import org.example.model.NotaFalta;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.function.Supplier;

public class SistemaAcademicoGUI extends JFrame {

    // ── Controllers ─────────────────────────────────────────────────────────────
    private final AlunoController     alunoController     = new AlunoController();
    private final NotaFaltaController notaFaltaController = new NotaFaltaController();

    // ── Painéis ──────────────────────────────────────────────────────────────────
    private JTabbedPane tabbedPane;
    private JPanel painelDadosPessoais, painelCurso, painelNotas, painelBoletim;

    // ── Campos globais (aba Dados Pessoais + Curso) ───────────────────────────────
    private JTextField          txtRGM, txtNome, txtEmail, txtEnd, txtMun, txtCampus;
    private JFormattedTextField txtData, txtCPF, txtCel;
    private JComboBox<String>   comboUF, comboCurso;
    private JRadioButton        rbMatutino, rbVespertino, rbNoturno;

    // ── Campos da aba Notas e Faltas ──────────────────────────────────────────────
    private JTextField        txtRgmNotas, txtNomeNotas, txtCursoNotas, txtFaltas;
    private JComboBox<String> comboDisc, comboSemestre, comboNota;

    // ── Boletim ───────────────────────────────────────────────────────────────────
    private JTextField  txtRgmBoletim;
    private JTextArea   areaBoletim;

    // ─────────────────────────────────────────────────────────────────────────────

    public SistemaAcademicoGUI() {
        setTitle("Sistema de Cadastro Acadêmico");
        setSize(720, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        configurarMenuSuperior();

        tabbedPane = new JTabbedPane();
        configurarAbaDadosPessoais();
        configurarAbaCurso();
        configurarAbaNotasFaltas();
        configurarAbaBoletim();

        tabbedPane.addTab("Dados Pessoais", painelDadosPessoais);
        tabbedPane.addTab("Curso",          painelCurso);
        tabbedPane.addTab("Notas e Faltas", painelNotas);
        tabbedPane.addTab("Boletim",        painelBoletim);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // MENU
    // ═════════════════════════════════════════════════════════════════════════════

    private void configurarMenuSuperior() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Aluno
        JMenu menuAluno = new JMenu("Aluno");
        JMenuItem miASalvar    = new JMenuItem("Salvar");
        JMenuItem miAAlterar   = new JMenuItem("Alterar");
        JMenuItem miAConsultar = new JMenuItem("Consultar");
        JMenuItem miAExcluir   = new JMenuItem("Excluir");
        JMenuItem miASair      = new JMenuItem("Sair");

        miASalvar.addActionListener(e    -> acaoSalvarAluno());
        miAAlterar.addActionListener(e   -> acaoAlterarAluno());
        miAConsultar.addActionListener(e -> acaoConsultarAluno());
        miAExcluir.addActionListener(e   -> acaoExcluirAluno());
        miASair.addActionListener(e      -> System.exit(0));

        menuAluno.add(miASalvar); menuAluno.add(miAAlterar);
        menuAluno.add(miAConsultar); menuAluno.add(miAExcluir);
        menuAluno.addSeparator(); menuAluno.add(miASair);

        // Menu Notas e Faltas
        JMenu menuNotas = new JMenu("Notas e Faltas");
        JMenuItem miNSalvar    = new JMenuItem("Salvar");
        JMenuItem miNAlterar   = new JMenuItem("Alterar");
        JMenuItem miNConsultar = new JMenuItem("Consultar");
        JMenuItem miNExcluir   = new JMenuItem("Excluir");
        JMenuItem miNSair      = new JMenuItem("Sair");

        miNSalvar.addActionListener(e    -> acaoSalvarNota());
        miNAlterar.addActionListener(e   -> acaoAlterarNota());
        miNConsultar.addActionListener(e -> acaoConsultarNota());
        miNExcluir.addActionListener(e   -> acaoExcluirNota());
        miNSair.addActionListener(e      -> System.exit(0));

        menuNotas.add(miNSalvar); menuNotas.add(miNAlterar);
        menuNotas.add(miNConsultar); menuNotas.add(miNExcluir);
        menuNotas.addSeparator(); menuNotas.add(miNSair);

        // Menu Ajuda
        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem miSobre = new JMenuItem("Sobre");
        miSobre.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Sistema Acadêmico v1.0\nDesenvolvido com Java + MySQL", "Sobre", JOptionPane.INFORMATION_MESSAGE));
        menuAjuda.add(miSobre);

        menuBar.add(menuAluno); menuBar.add(menuNotas); menuBar.add(menuAjuda);
        setJMenuBar(menuBar);
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // ABA DADOS PESSOAIS
    // ═════════════════════════════════════════════════════════════════════════════

    private void configurarAbaDadosPessoais() {
        painelDadosPessoais = new JPanel(null);

        // RGM
        addLabel(painelDadosPessoais, "RGM",  20, 20, 40, 25);
        txtRGM = addTextField(painelDadosPessoais, 60, 20, 150, 25);

        // Nome
        addLabel(painelDadosPessoais, "Nome", 230, 20, 40, 25);
        txtNome = addTextField(painelDadosPessoais, 280, 20, 400, 25);

        // Data de Nascimento
        addLabel(painelDadosPessoais, "Data de Nascimento", 20, 60, 120, 25);
        txtData = addFormattedField(painelDadosPessoais, "##/##/####", 140, 60, 100, 25);

        // CPF
        addLabel(painelDadosPessoais, "CPF", 260, 60, 30, 25);
        txtCPF = addFormattedField(painelDadosPessoais, "###.###.###-##", 300, 60, 150, 25);

        // Email
        addLabel(painelDadosPessoais, "Email", 20, 100, 40, 25);
        txtEmail = addTextField(painelDadosPessoais, 60, 100, 600, 25);

        // Endereço
        addLabel(painelDadosPessoais, "End.", 20, 140, 40, 25);
        txtEnd = addTextField(painelDadosPessoais, 60, 140, 600, 25);

        // Município / UF / Celular
        addLabel(painelDadosPessoais, "Município", 20, 180, 60, 25);
        txtMun = addTextField(painelDadosPessoais, 90, 180, 200, 25);

        addLabel(painelDadosPessoais, "UF", 310, 180, 20, 25);
        comboUF = new JComboBox<>(new String[]{"SP","RJ","MG","PR","SC","RS","BA","GO","DF","PE"});
        comboUF.setBounds(340, 180, 60, 25);
        painelDadosPessoais.add(comboUF);

        addLabel(painelDadosPessoais, "Celular", 420, 180, 50, 25);
        txtCel = addFormattedField(painelDadosPessoais, "(##)#####-####", 470, 180, 190, 25);
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // ABA CURSO  (contém os botões de CRUD do Aluno)
    // ═════════════════════════════════════════════════════════════════════════════

    private void configurarAbaCurso() {
        painelCurso = new JPanel(null);

        addLabel(painelCurso, "Curso", 20, 30, 50, 25);
        comboCurso = new JComboBox<>(new String[]{
                "Análise e Desenvolvimento de Sistemas",
                "Gestão de TI",
                "Ciência da Computação",
                "Sistemas de Informação",
                "Engenharia de Software"
        });
        comboCurso.setBounds(80, 30, 420, 25);
        painelCurso.add(comboCurso);

        addLabel(painelCurso, "Campus", 20, 80, 60, 25);
        txtCampus = addTextField(painelCurso, 80, 80, 200, 25);
        txtCampus.setText("Tatuapé");
        txtCampus.setEditable(false);

        addLabel(painelCurso, "Período", 20, 130, 60, 25);
        rbMatutino   = new JRadioButton("Matutino");
        rbVespertino = new JRadioButton("Vespertino");
        rbNoturno    = new JRadioButton("Noturno");
        ButtonGroup grupoPeriodo = new ButtonGroup();
        grupoPeriodo.add(rbMatutino); grupoPeriodo.add(rbVespertino); grupoPeriodo.add(rbNoturno);
        rbMatutino.setBounds(80, 130, 100, 25);
        rbVespertino.setBounds(190, 130, 110, 25);
        rbNoturno.setBounds(310, 130, 100, 25);
        painelCurso.add(rbMatutino); painelCurso.add(rbVespertino); painelCurso.add(rbNoturno);

        // Botões
        JButton btnSalvar    = criarBotao("Salvar",    "/icons/salvar.png",    20, 200);
        JButton btnAlterar   = criarBotao("Alterar",   "/icons/alterar.png",  140, 200);
        JButton btnConsultar = criarBotao("Consultar", "/icons/consultar.png",260, 200);
        JButton btnExcluir   = criarBotao("Excluir",   "/icons/excluir.png",  380, 200);
        JButton btnSair      = criarBotao("Sair",      "/icons/sair.png",     500, 200);

        btnSalvar.addActionListener(e    -> acaoSalvarAluno());
        btnAlterar.addActionListener(e   -> acaoAlterarAluno());
        btnConsultar.addActionListener(e -> acaoConsultarAluno());
        btnExcluir.addActionListener(e   -> acaoExcluirAluno());
        btnSair.addActionListener(e      -> System.exit(0));

        painelCurso.add(btnSalvar); painelCurso.add(btnAlterar);
        painelCurso.add(btnConsultar); painelCurso.add(btnExcluir); painelCurso.add(btnSair);
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // ABA NOTAS E FALTAS
    // ═════════════════════════════════════════════════════════════════════════════

    private void configurarAbaNotasFaltas() {
        painelNotas = new JPanel(null);

        // Linha 1: RGM + Nome do aluno
        addLabel(painelNotas, "RGM", 20, 20, 30, 25);
        txtRgmNotas = addTextField(painelNotas, 55, 20, 100, 25);

        txtNomeNotas = new JTextField("Aguardando RGM...");
        txtNomeNotas.setBounds(165, 20, 510, 25);
        txtNomeNotas.setEditable(false);
        painelNotas.add(txtNomeNotas);

        // Linha 2: Curso do aluno
        txtCursoNotas = new JTextField("Aguardando RGM...");
        txtCursoNotas.setBounds(20, 55, 655, 25);
        txtCursoNotas.setEditable(false);
        painelNotas.add(txtCursoNotas);

        // Auto-preenchimento ao sair do campo RGM
        txtRgmNotas.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String rgm = txtRgmNotas.getText().trim();
                if (rgm.isEmpty()) {
                    txtNomeNotas.setText("Aguardando RGM...");
                    txtCursoNotas.setText("Aguardando RGM...");
                    return;
                }
                try {
                    Aluno a = alunoController.buscarPorRgm(rgm);
                    if (a != null) {
                        txtNomeNotas.setText(a.getNome());
                        txtCursoNotas.setText(a.getCurso() + " — " + a.getCampus() + " (" + a.getPeriodo() + ")");
                    } else {
                        txtNomeNotas.setText("RGM NÃO ENCONTRADO");
                        txtCursoNotas.setText("-");
                    }
                } catch (SQLException ex) {
                    txtNomeNotas.setText("Erro ao buscar no banco");
                    ex.printStackTrace();
                }
            }
        });

        // Linha 3: Disciplina
        addLabel(painelNotas, "Disciplina", 20, 100, 65, 25);
        comboDisc = new JComboBox<>(new String[]{
                "Programação Orientada a Objetos",
                "Banco de Dados",
                "Engenharia de Software",
                "Estrutura de Dados",
                "Redes de Computadores"
        });
        comboDisc.setBounds(90, 100, 300, 25);
        painelNotas.add(comboDisc);

        // Linha 4: Semestre | Nota | Faltas
        addLabel(painelNotas, "Semestre", 20, 145, 60, 25);
        comboSemestre = new JComboBox<>(new String[]{
                "2023-1","2023-2","2024-1","2024-2","2025-1","2025-2"
        });
        comboSemestre.setBounds(85, 145, 80, 25);
        painelNotas.add(comboSemestre);

        addLabel(painelNotas, "Nota", 185, 145, 30, 25);
        comboNota = new JComboBox<>(new String[]{
                "0.0","0.5","1.0","1.5","2.0","2.5","3.0","3.5",
                "4.0","4.5","5.0","5.5","6.0","6.5","7.0","7.5",
                "8.0","8.5","9.0","9.5","10.0"
        });
        comboNota.setBounds(220, 145, 70, 25);
        painelNotas.add(comboNota);

        addLabel(painelNotas, "Faltas", 305, 145, 40, 25);
        txtFaltas = addTextField(painelNotas, 350, 145, 60, 25);

        // Botões
        JButton btnSalvar    = criarBotao("Salvar",    "/icons/salvar.png",    20, 200);
        JButton btnAlterar   = criarBotao("Alterar",   "/icons/alterar.png",  140, 200);
        JButton btnConsultar = criarBotao("Consultar", "/icons/consultar.png",260, 200);
        JButton btnExcluir   = criarBotao("Excluir",   "/icons/excluir.png",  380, 200);
        JButton btnSair      = criarBotao("Sair",      "/icons/sair.png",     500, 200);

        btnSalvar.addActionListener(e    -> acaoSalvarNota());
        btnAlterar.addActionListener(e   -> acaoAlterarNota());
        btnConsultar.addActionListener(e -> acaoConsultarNota());
        btnExcluir.addActionListener(e   -> acaoExcluirNota());
        btnSair.addActionListener(e      -> System.exit(0));

        painelNotas.add(btnSalvar); painelNotas.add(btnAlterar);
        painelNotas.add(btnConsultar); painelNotas.add(btnExcluir); painelNotas.add(btnSair);
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // ABA BOLETIM
    // ═════════════════════════════════════════════════════════════════════════════

    private void configurarAbaBoletim() {
        painelBoletim = new JPanel(new BorderLayout(8, 8));
        painelBoletim.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de busca no topo
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        topPanel.add(new JLabel("RGM:"));
        txtRgmBoletim = new JTextField(10);
        JButton btnGerarBoletim = new JButton("Gerar Boletim");
        topPanel.add(txtRgmBoletim);
        topPanel.add(btnGerarBoletim);

        areaBoletim = new JTextArea();
        areaBoletim.setEditable(false);
        areaBoletim.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaBoletim.setText("Digite um RGM e clique em 'Gerar Boletim'.");

        btnGerarBoletim.addActionListener(e -> gerarBoletim());

        painelBoletim.add(topPanel,                       BorderLayout.NORTH);
        painelBoletim.add(new JScrollPane(areaBoletim),   BorderLayout.CENTER);
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // AÇÕES DE CRUD — ALUNO
    // ═════════════════════════════════════════════════════════════════════════════

    private void acaoSalvarAluno() {
        String resultado = alunoController.salvarAluno(criarAluno());
        JOptionPane.showMessageDialog(this, resultado);
        if (resultado.startsWith("SUCESSO")) limparCampos();
    }

    private void acaoAlterarAluno() {
        String resultado = alunoController.alterarAluno(criarAluno());
        JOptionPane.showMessageDialog(this, resultado);
        if (resultado.startsWith("SUCESSO")) limparCampos();
    }

    private void acaoConsultarAluno() {
        String rgm = txtRGM.getText().trim();
        if (rgm.isEmpty()) { JOptionPane.showMessageDialog(this, "Informe um RGM para consultar."); return; }
        try {
            Aluno a = alunoController.buscarPorRgm(rgm);
            if (a != null) preencherTela(a);
            else JOptionPane.showMessageDialog(this, "Aluno não encontrado para RGM: " + rgm);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao consultar: " + ex.getMessage());
        }
    }

    private void acaoExcluirAluno() {
        String rgm = txtRGM.getText().trim();
        if (rgm.isEmpty()) { JOptionPane.showMessageDialog(this, "Informe um RGM para excluir."); return; }
        int ok = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o aluno RGM: " + rgm + "?\nTodas as notas e faltas também serão removidas.",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, alunoController.excluirAluno(rgm));
            limparCampos();
        }
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // AÇÕES DE CRUD — NOTAS E FALTAS
    // ═════════════════════════════════════════════════════════════════════════════

    private void acaoSalvarNota() {
        if (!rgmNotasValido()) return;
        JOptionPane.showMessageDialog(this, notaFaltaController.salvarNotaFalta(notaDaTela()));
    }

    private void acaoAlterarNota() {
        if (!rgmNotasValido()) return;
        JOptionPane.showMessageDialog(this, notaFaltaController.alterarNotaFalta(notaDaTela()));
    }

    private void acaoConsultarNota() {
        if (!rgmNotasValido()) return;
        String rgm        = txtRgmNotas.getText().trim();
        String disciplina = (String) comboDisc.getSelectedItem();
        String semestre   = (String) comboSemestre.getSelectedItem();
        try {
            NotaFalta nf = notaFaltaController.buscarNotaFalta(rgm, disciplina, semestre);
            if (nf != null) {
                comboNota.setSelectedItem(String.valueOf(nf.getNota()));
                txtFaltas.setText(String.valueOf(nf.getFaltas()));
                JOptionPane.showMessageDialog(this, "Dados carregados!");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum registro para esta disciplina/semestre.");
                txtFaltas.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void acaoExcluirNota() {
        if (!rgmNotasValido()) return;
        String rgm        = txtRgmNotas.getText().trim();
        String disciplina = (String) comboDisc.getSelectedItem();
        String semestre   = (String) comboSemestre.getSelectedItem();
        int ok = JOptionPane.showConfirmDialog(this,
                "Excluir nota de \"" + disciplina + "\" (" + semestre + ") para RGM " + rgm + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, notaFaltaController.excluirNotaFalta(rgm, disciplina, semestre));
            txtFaltas.setText("");
        }
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // BOLETIM
    // ═════════════════════════════════════════════════════════════════════════════

    private void gerarBoletim() {
        String rgm = txtRgmBoletim.getText().trim();
        if (rgm.isEmpty()) { JOptionPane.showMessageDialog(this, "Informe um RGM."); return; }
        try {
            Aluno a = alunoController.buscarPorRgm(rgm);
            if (a == null) { areaBoletim.setText("Aluno não encontrado."); return; }

            List<NotaFalta> lista = notaFaltaController.listarPorRgm(rgm);

            StringBuilder sb = new StringBuilder();
            sb.append("╔══════════════════════════════════════════════════════╗\n");
            sb.append("║           BOLETIM ACADÊMICO                         ║\n");
            sb.append("╚══════════════════════════════════════════════════════╝\n\n");
            sb.append(String.format("RGM    : %s%n", a.getRgm()));
            sb.append(String.format("Nome   : %s%n", a.getNome()));
            sb.append(String.format("Curso  : %s%n", a.getCurso()));
            sb.append(String.format("Campus : %s  |  Período: %s%n%n", a.getCampus(), a.getPeriodo()));
            sb.append(String.format("%-35s | %-8s | %-8s | %s%n", "DISCIPLINA", "SEMESTRE", "NOTA", "FALTAS"));
            sb.append("─".repeat(70)).append("\n");

            if (lista.isEmpty()) {
                sb.append("Nenhum lançamento encontrado.\n");
            } else {
                for (NotaFalta nf : lista) {
                    String situacao = nf.getNota() >= 5.0 ? "✔ Aprovado" : "✖ Reprovado";
                    sb.append(String.format("%-35s | %-8s | %-8.1f | %d falta(s)  %s%n",
                            nf.getDisciplina(), nf.getSemestre(), nf.getNota(), nf.getFaltas(), situacao));
                }
            }
            areaBoletim.setText(sb.toString());
            areaBoletim.setCaretPosition(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
            areaBoletim.setText("Erro ao gerar boletim: " + ex.getMessage());
        }
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // HELPERS — construção de objetos a partir da tela
    // ═════════════════════════════════════════════════════════════════════════════

    private Aluno criarAluno() {
        Aluno a = new Aluno();
        a.setRgm(txtRGM.getText().trim());
        a.setNome(txtNome.getText().trim());
        a.setEmail(txtEmail.getText().trim());
        a.setEndereco(txtEnd.getText().trim());
        a.setMunicipio(txtMun.getText().trim());
        a.setUf((String) comboUF.getSelectedItem());
        a.setTelefone(txtCel.getText());
        a.setCurso((String) comboCurso.getSelectedItem());
        a.setCampus(txtCampus.getText().trim());
        a.setCpf(txtCPF.getText().replace(".", "").replace("-", ""));

        // Converte DD/MM/AAAA → AAAA-MM-DD para o banco
        String dataTela = txtData.getText();
        if (dataTela != null && dataTela.length() == 10 && !dataTela.contains("_")) {
            String[] p = dataTela.split("/");
            a.setDataNascimento(p[2] + "-" + p[1] + "-" + p[0]);
        }

        if      (rbMatutino.isSelected())   a.setPeriodo("Matutino");
        else if (rbVespertino.isSelected())  a.setPeriodo("Vespertino");
        else if (rbNoturno.isSelected())     a.setPeriodo("Noturno");

        return a;
    }

    private NotaFalta notaDaTela() {
        NotaFalta nf = new NotaFalta();
        nf.setRgm(txtRgmNotas.getText().trim());
        nf.setDisciplina((String) comboDisc.getSelectedItem());
        nf.setSemestre((String) comboSemestre.getSelectedItem());
        try {
            nf.setNota(Double.parseDouble(((String) comboNota.getSelectedItem()).replace(",", ".")));
        } catch (NumberFormatException e) {
            nf.setNota(0.0);
        }
        try {
            nf.setFaltas(Integer.parseInt(txtFaltas.getText().trim()));
        } catch (NumberFormatException e) {
            nf.setFaltas(0);
        }
        return nf;
    }

    private boolean rgmNotasValido() {
        String rgm = txtRgmNotas.getText().trim();
        if (rgm.isEmpty() || txtNomeNotas.getText().contains("NÃO ENCONTRADO")
                          || txtNomeNotas.getText().contains("Aguardando")) {
            JOptionPane.showMessageDialog(this, "Informe um RGM válido primeiro.");
            return false;
        }
        return true;
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // HELPERS — preenchimento / limpeza da tela
    // ═════════════════════════════════════════════════════════════════════════════

    private void preencherTela(Aluno a) {
        txtNome.setText(a.getNome());
        txtEmail.setText(a.getEmail());
        txtEnd.setText(a.getEndereco());
        txtMun.setText(a.getMunicipio());
        comboUF.setSelectedItem(a.getUf());
        comboCurso.setSelectedItem(a.getCurso());
        txtCampus.setText(a.getCampus());

        if (a.getCpf()      != null) txtCPF.setText(a.getCpf());
        if (a.getTelefone() != null) txtCel.setText(a.getTelefone());

        // Converte AAAA-MM-DD → DD/MM/AAAA para exibição
        String dataBanco = a.getDataNascimento();
        if (dataBanco != null && dataBanco.length() == 10) {
            String[] p = dataBanco.split("-");
            txtData.setText(p[2] + "/" + p[1] + "/" + p[0]);
        } else {
            txtData.setText("");
        }

        if      ("Matutino".equalsIgnoreCase(a.getPeriodo()))   rbMatutino.setSelected(true);
        else if ("Vespertino".equalsIgnoreCase(a.getPeriodo())) rbVespertino.setSelected(true);
        else if ("Noturno".equalsIgnoreCase(a.getPeriodo()))    rbNoturno.setSelected(true);

        // Muda para a aba Curso para o usuário ver todos os dados
        tabbedPane.setSelectedIndex(1);
    }

    private void limparCampos() {
        txtRGM.setText(""); txtNome.setText(""); txtData.setText("");
        txtCPF.setText(""); txtEmail.setText(""); txtEnd.setText("");
        txtMun.setText(""); txtCel.setText(""); txtCampus.setText("Tatuapé");
        comboUF.setSelectedIndex(0); comboCurso.setSelectedIndex(0);
        rbMatutino.setSelected(false); rbVespertino.setSelected(false); rbNoturno.setSelected(false);
        txtRGM.requestFocus();
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // HELPERS — fábrica de componentes
    // ═════════════════════════════════════════════════════════════════════════════

    private JLabel addLabel(JPanel p, String texto, int x, int y, int w, int h) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, w, h);
        p.add(lbl);
        return lbl;
    }

    private JTextField addTextField(JPanel p, int x, int y, int w, int h) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, w, h);
        p.add(tf);
        return tf;
    }

    private JFormattedTextField addFormattedField(JPanel p, String mascara, int x, int y, int w, int h) {
        JFormattedTextField ftf = new JFormattedTextField(criarMascara(mascara));
        ftf.setBounds(x, y, w, h);
        p.add(ftf);
        return ftf;
    }

    private JButton criarBotao(String texto, String iconePath, int x, int y) {
        JButton btn = new JButton(texto, carregarIcone(iconePath));
        btn.setBounds(x, y, 110, 40);
        return btn;
    }

    private MaskFormatter criarMascara(String mascara) {
        try {
            MaskFormatter mf = new MaskFormatter(mascara);
            mf.setPlaceholderCharacter('_');
            return mf;
        } catch (ParseException e) {
            return null;
        }
    }

    private ImageIcon carregarIcone(String caminho) {
        java.net.URL url = getClass().getResource(caminho);
        return url != null ? new ImageIcon(url) : null;
    }

    // ═════════════════════════════════════════════════════════════════════════════
    // MAIN
    // ═════════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new SistemaAcademicoGUI().setVisible(true));
    }
}
