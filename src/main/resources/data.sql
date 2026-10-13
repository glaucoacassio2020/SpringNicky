INSERT INTO empresas (nome, slug, site, descricao) VALUES ('Aurora Tech', 'aurora-tech', 'https://aurora.tech', 'Produto de gestão para clínicas. Time pequeno, remoto desde 2020.');
INSERT INTO empresas (nome, slug, site, descricao) VALUES ('Nuvem Rosa', 'nuvem-rosa', 'https://nuvemrosa.com.br', 'Consultoria de dados para o varejo do Nordeste.');
INSERT INTO empresas (nome, slug, site, descricao) VALUES ('Mareh Digital', 'mareh-digital', 'https://mareh.digital', 'Agência de produtos digitais em Olinda.');
INSERT INTO empresas (nome, slug, site, descricao) VALUES ('Coral Labs', 'coral-labs', 'https://corallabs.io', 'Plataforma de educação corporativa.');
INSERT INTO empresas (nome, slug, site, descricao) VALUES ('Tucano Systems', 'tucano-systems', 'https://tucano.sys.br', 'Integrações e APIs para logística.');

INSERT INTO pessoas (nome, email, area, senioridade) VALUES ('Ana Nogueira', 'ana@exemplo.com', 'Front-end', 'Júnior');
INSERT INTO pessoas (nome, email, area, senioridade) VALUES ('Bruno Sales', 'bruno@exemplo.com', 'Back-end', 'Júnior');
INSERT INTO pessoas (nome, email, area, senioridade) VALUES ('Carla Menezes', 'carla@exemplo.com', 'Dados', 'Estágio');
INSERT INTO pessoas (nome, email, area, senioridade) VALUES ('Diego Tavares', 'diego@exemplo.com', 'Front-end', 'Pleno');
INSERT INTO pessoas (nome, email, area, senioridade) VALUES ('Elisa Prado', 'elisa@exemplo.com', 'Mobile', 'Júnior');
INSERT INTO pessoas (nome, email, area, senioridade) VALUES ('Fábio Vasconcelos', 'fabio@exemplo.com', 'Back-end', 'Pleno');

INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Desenvolvedora Front-end Júnior', 'Telas do produto em React e Next.js, pareando com gente mais experiente.', 'Front-end', 'Júnior', 'Remoto', true, 'aurora-tech');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Estágio em Front-end', 'Primeiro contato com HTML, CSS e JavaScript num time de agência.', 'Front-end', 'Estágio', 'Presencial · Olinda', true, 'mareh-digital');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Desenvolvedora Front-end Pleno', 'Dono de features inteiras, do desenho à publicação.', 'Front-end', 'Pleno', 'Remoto', false, 'nuvem-rosa');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Desenvolvedora React Júnior', 'Interface da plataforma de cursos, com foco em acessibilidade.', 'Front-end', 'Júnior', 'Híbrido · Recife', true, 'coral-labs');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Desenvolvedora Java Júnior', 'APIs REST com Spring Boot, integrando transportadoras.', 'Back-end', 'Júnior', 'Remoto', true, 'tucano-systems');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Desenvolvedora Back-end Pleno', 'Serviços de agendamento e faturamento, com fila e cache.', 'Back-end', 'Pleno', 'Remoto', false, 'aurora-tech');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Desenvolvedora Node Pleno', 'Serviço de trilhas e progresso, em TypeScript.', 'Back-end', 'Pleno', 'Híbrido · Recife', false, 'coral-labs');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Analista de Dados Júnior', 'SQL, planilhas e construção de painéis para clientes do varejo.', 'Dados', 'Júnior', 'Híbrido · Recife', true, 'nuvem-rosa');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Estágio em Dados', 'Limpeza de base e primeiros painéis, com acompanhamento semanal.', 'Dados', 'Estágio', 'Remoto', true, 'nuvem-rosa');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Engenheira de Dados Pleno', 'Pipelines de rastreamento de carga, em Python e SQL.', 'Dados', 'Pleno', 'Remoto', false, 'tucano-systems');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Desenvolvedora Mobile Júnior', 'Aplicativos em React Native para clientes da agência.', 'Mobile', 'Júnior', 'Remoto', true, 'mareh-digital');
INSERT INTO vagas (titulo, descricao, area, senioridade, local, aceita_iniciante, empresa_slug) VALUES ('Pessoa Desenvolvedora Android Júnior', 'Aplicativo de agenda para as clínicas parceiras.', 'Mobile', 'Júnior', 'Presencial · Recife', false, 'aurora-tech');
