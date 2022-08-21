CREATE TABLE public.funcionalidades (
	id bigserial NOT NULL,
	nome varchar(255) NOT NULL,
	CONSTRAINT funcionalidades_pkey PRIMARY KEY (id)
);

CREATE TABLE public.perfis (
	id bigserial NOT NULL,
	nome varchar(255) NULL,
	CONSTRAINT perfis_pkey PRIMARY KEY (id)
);

CREATE TABLE public.perfis_funcionalidades (
	perfil_id int8 NOT NULL,
	funcionalidades_id int8 NOT NULL
);


-- public.perfis_funcionalidades foreign keys

ALTER TABLE public.perfis_funcionalidades ADD CONSTRAINT fk7n7p0f5aylaaw8eud8wlv9f0l FOREIGN KEY (funcionalidades_id) REFERENCES public.funcionalidades(id);
ALTER TABLE public.perfis_funcionalidades ADD CONSTRAINT fkj1el1x40rt65ww1t09t6t0dgh FOREIGN KEY (perfil_id) REFERENCES public.perfis(id);

CREATE TABLE public.usuarios (
	id bigserial NOT NULL,
	data_atualizacao timestamp NOT NULL,
	data_criacao timestamp NOT NULL,
	login varchar(255) NOT NULL,
	senha varchar(255) NOT NULL,
	uuid varchar(255) NOT NULL,
	CONSTRAINT uk_r8oo98o39ykr4hi57md9nibmw UNIQUE (login),
	CONSTRAINT usuarios_pkey PRIMARY KEY (id)
);

CREATE TABLE public.usuarios_perfis (
	usuario_id int8 NOT NULL,
	perfis_id int8 NOT NULL
);


-- public.usuarios_perfis foreign keys

ALTER TABLE public.usuarios_perfis ADD CONSTRAINT fkcvq90lk95py1n889uimu18atx FOREIGN KEY (perfis_id) REFERENCES public.perfis(id);
ALTER TABLE public.usuarios_perfis ADD CONSTRAINT fkotpfkn8c9nmhqqy4pb8hkgp18 FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id);
