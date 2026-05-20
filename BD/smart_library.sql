--
-- PostgreSQL database dump
--

\restrict 5PBdTcwtneFdHo9Pm3HkBoUi7Csmt3UyFf4SlasMfLFuTHzW7ar0816C7Yjl15e

-- Dumped from database version 17.9
-- Dumped by pg_dump version 17.9

-- Started on 2026-05-19 21:18:20

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 16453)
-- Name: estudiante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estudiante (
    id integer NOT NULL,
    nombre character varying(100),
    email character varying(100),
    edad integer
);


ALTER TABLE public.estudiante OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16447)
-- Name: libro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.libro (
    id integer NOT NULL,
    titulo character varying(80),
    autor character varying(80),
    paginas integer
);


ALTER TABLE public.libro OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16458)
-- Name: prestamo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prestamo (
    id integer NOT NULL,
    libro character varying(100),
    usuario character varying(80),
    dias integer
);


ALTER TABLE public.prestamo OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16444)
-- Name: seq_estudiante; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_estudiante
    START WITH 1001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_estudiante OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16390)
-- Name: seq_libro; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_libro
    START WITH 1001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_libro OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16445)
-- Name: seq_prestamo; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_prestamo
    START WITH 1001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_prestamo OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16446)
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_usuario
    START WITH 1001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_usuario OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16463)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    nombre character varying(100),
    tipo character varying(80),
    puntos integer
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 4915 (class 0 OID 16453)
-- Dependencies: 222
-- Data for Name: estudiante; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estudiante (id, nombre, email, edad) FROM stdin;
1001	Jesus Laura	jesus@gmail.com	28
\.


--
-- TOC entry 4914 (class 0 OID 16447)
-- Dependencies: 221
-- Data for Name: libro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.libro (id, titulo, autor, paginas) FROM stdin;
\.


--
-- TOC entry 4916 (class 0 OID 16458)
-- Dependencies: 223
-- Data for Name: prestamo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.prestamo (id, libro, usuario, dias) FROM stdin;
\.


--
-- TOC entry 4917 (class 0 OID 16463)
-- Dependencies: 224
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id, nombre, tipo, puntos) FROM stdin;
\.


--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 218
-- Name: seq_estudiante; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_estudiante', 1001, true);


--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 217
-- Name: seq_libro; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_libro', 1001, false);


--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 219
-- Name: seq_prestamo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_prestamo', 1001, false);


--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 220
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_usuario', 1001, false);


--
-- TOC entry 4760 (class 2606 OID 16457)
-- Name: estudiante estudiante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estudiante
    ADD CONSTRAINT estudiante_pkey PRIMARY KEY (id);


--
-- TOC entry 4758 (class 2606 OID 16451)
-- Name: libro libro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.libro
    ADD CONSTRAINT libro_pkey PRIMARY KEY (id);


--
-- TOC entry 4762 (class 2606 OID 16462)
-- Name: prestamo prestamo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prestamo
    ADD CONSTRAINT prestamo_pkey PRIMARY KEY (id);


--
-- TOC entry 4764 (class 2606 OID 16467)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


-- Completed on 2026-05-19 21:18:20

--
-- PostgreSQL database dump complete
--

\unrestrict 5PBdTcwtneFdHo9Pm3HkBoUi7Csmt3UyFf4SlasMfLFuTHzW7ar0816C7Yjl15e

