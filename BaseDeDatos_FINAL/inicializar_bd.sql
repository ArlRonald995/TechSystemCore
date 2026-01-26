--
-- PostgreSQL database dump
--

\restrict TRzqssSwWznAb9IaPqZZ8tgZ5wLkC1DoPjd4V7oHAQLDQCSJ1guAm3xToNOga7v

-- Dumped from database version 15.15 (Debian 15.15-1.pgdg13+1)
-- Dumped by pg_dump version 15.15 (Debian 15.15-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.valoraciones DROP CONSTRAINT valoraciones_usuario_id_fkey;
ALTER TABLE ONLY public.valoraciones DROP CONSTRAINT valoraciones_producto_sku_fkey;
ALTER TABLE ONLY public.pedidos DROP CONSTRAINT pedidos_usuario_id_fkey;
ALTER TABLE ONLY public.detalle_pedidos DROP CONSTRAINT detalle_pedidos_producto_sku_fkey;
ALTER TABLE ONLY public.detalle_pedidos DROP CONSTRAINT detalle_pedidos_pedido_id_fkey;
ALTER TABLE ONLY public.valoraciones DROP CONSTRAINT valoraciones_pkey;
ALTER TABLE ONLY public.usuarios DROP CONSTRAINT usuarios_pkey;
ALTER TABLE ONLY public.usuarios DROP CONSTRAINT usuarios_email_key;
ALTER TABLE ONLY public.productostienda_raw DROP CONSTRAINT productostienda_raw_pkey;
ALTER TABLE ONLY public.productostienda_raw DROP CONSTRAINT productos_sku_unique;
ALTER TABLE ONLY public.pedidos DROP CONSTRAINT pedidos_pkey;
ALTER TABLE ONLY public.detalle_pedidos DROP CONSTRAINT detalle_pedidos_pkey;
ALTER TABLE public.valoraciones ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.usuarios ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.productostienda_raw ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.pedidos ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.detalle_pedidos ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE public.valoraciones_id_seq;
DROP TABLE public.valoraciones;
DROP SEQUENCE public.usuarios_id_seq;
DROP TABLE public.usuarios;
DROP SEQUENCE public.productostienda_raw_id_seq;
DROP TABLE public.productostienda_raw;
DROP SEQUENCE public.pedidos_id_seq;
DROP TABLE public.pedidos;
DROP SEQUENCE public.detalle_pedidos_id_seq;
DROP TABLE public.detalle_pedidos;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: detalle_pedidos; Type: TABLE; Schema: public; Owner: AdminTienda
--

CREATE TABLE public.detalle_pedidos (
    id integer NOT NULL,
    pedido_id integer,
    producto_sku character varying(50),
    cantidad integer NOT NULL,
    precio_unitario numeric(10,2) NOT NULL
);


ALTER TABLE public.detalle_pedidos OWNER TO "AdminTienda";

--
-- Name: detalle_pedidos_id_seq; Type: SEQUENCE; Schema: public; Owner: AdminTienda
--

CREATE SEQUENCE public.detalle_pedidos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.detalle_pedidos_id_seq OWNER TO "AdminTienda";

--
-- Name: detalle_pedidos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: AdminTienda
--

ALTER SEQUENCE public.detalle_pedidos_id_seq OWNED BY public.detalle_pedidos.id;


--
-- Name: pedidos; Type: TABLE; Schema: public; Owner: AdminTienda
--

CREATE TABLE public.pedidos (
    id integer NOT NULL,
    usuario_id integer,
    fecha_compra timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    total numeric(10,2) NOT NULL,
    estado_envio character varying(50) DEFAULT 'En Bodega'::character varying,
    ubicacion_actual character varying(100) DEFAULT 'Almacén Central'::character varying,
    fecha_estimada date
);


ALTER TABLE public.pedidos OWNER TO "AdminTienda";

--
-- Name: pedidos_id_seq; Type: SEQUENCE; Schema: public; Owner: AdminTienda
--

CREATE SEQUENCE public.pedidos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pedidos_id_seq OWNER TO "AdminTienda";

--
-- Name: pedidos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: AdminTienda
--

ALTER SEQUENCE public.pedidos_id_seq OWNED BY public.pedidos.id;


--
-- Name: productostienda_raw; Type: TABLE; Schema: public; Owner: AdminTienda
--

CREATE TABLE public.productostienda_raw (
    id integer NOT NULL,
    tipo text,
    sku text,
    nombre text,
    marca text,
    precio numeric(10,2),
    stock integer,
    descripcion text,
    especificaciones text
);


ALTER TABLE public.productostienda_raw OWNER TO "AdminTienda";

--
-- Name: productostienda_raw_id_seq; Type: SEQUENCE; Schema: public; Owner: AdminTienda
--

CREATE SEQUENCE public.productostienda_raw_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.productostienda_raw_id_seq OWNER TO "AdminTienda";

--
-- Name: productostienda_raw_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: AdminTienda
--

ALTER SEQUENCE public.productostienda_raw_id_seq OWNED BY public.productostienda_raw.id;


--
-- Name: usuarios; Type: TABLE; Schema: public; Owner: AdminTienda
--

CREATE TABLE public.usuarios (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    rol character varying(20) DEFAULT 'cliente'::character varying,
    direccion text
);


ALTER TABLE public.usuarios OWNER TO "AdminTienda";

--
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: public; Owner: AdminTienda
--

CREATE SEQUENCE public.usuarios_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_id_seq OWNER TO "AdminTienda";

--
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: AdminTienda
--

ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;


--
-- Name: valoraciones; Type: TABLE; Schema: public; Owner: AdminTienda
--

CREATE TABLE public.valoraciones (
    id integer NOT NULL,
    usuario_id integer,
    producto_sku character varying(50),
    puntuacion integer,
    comentario text,
    fecha date DEFAULT CURRENT_DATE,
    CONSTRAINT valoraciones_puntuacion_check CHECK (((puntuacion >= 1) AND (puntuacion <= 5)))
);


ALTER TABLE public.valoraciones OWNER TO "AdminTienda";

--
-- Name: valoraciones_id_seq; Type: SEQUENCE; Schema: public; Owner: AdminTienda
--

CREATE SEQUENCE public.valoraciones_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.valoraciones_id_seq OWNER TO "AdminTienda";

--
-- Name: valoraciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: AdminTienda
--

ALTER SEQUENCE public.valoraciones_id_seq OWNED BY public.valoraciones.id;


--
-- Name: detalle_pedidos id; Type: DEFAULT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.detalle_pedidos ALTER COLUMN id SET DEFAULT nextval('public.detalle_pedidos_id_seq'::regclass);


--
-- Name: pedidos id; Type: DEFAULT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.pedidos ALTER COLUMN id SET DEFAULT nextval('public.pedidos_id_seq'::regclass);


--
-- Name: productostienda_raw id; Type: DEFAULT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.productostienda_raw ALTER COLUMN id SET DEFAULT nextval('public.productostienda_raw_id_seq'::regclass);


--
-- Name: usuarios id; Type: DEFAULT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);


--
-- Name: valoraciones id; Type: DEFAULT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.valoraciones ALTER COLUMN id SET DEFAULT nextval('public.valoraciones_id_seq'::regclass);


--
-- Data for Name: detalle_pedidos; Type: TABLE DATA; Schema: public; Owner: AdminTienda
--

COPY public.detalle_pedidos (id, pedido_id, producto_sku, cantidad, precio_unitario) FROM stdin;
1	1	MON-ASU-140	1	270.06
2	1	TAB-LEN-030	1	175.00
3	1	MOU-APP-122	1	113.35
4	1	CEL-HUA-032	1	1415.20
5	2	LAP-DELL-018	1	3100.00
6	2	CPU-AMD-051	1	581.70
7	2	MOU-APP-124	1	121.00
8	3	RAM-ADA-085	1	455.95
9	4	SSD-ADA-096	1	60.15
10	4	TAB-APP-009	1	921.52
11	5	LAP-LEN-078	1	875.65
\.


--
-- Data for Name: pedidos; Type: TABLE DATA; Schema: public; Owner: AdminTienda
--

COPY public.pedidos (id, usuario_id, fecha_compra, total, estado_envio, ubicacion_actual, fecha_estimada) FROM stdin;
1	3	2026-01-23 23:08:45.839982	1973.61	Entregado	Domicilio Cliente	\N
2	2	2026-01-23 23:25:10.478116	3802.70	Entregado	Domicilio Cliente	\N
3	2	2026-01-23 23:47:15.472193	455.95	Entregado	La Quintana	\N
4	2	2026-01-24 00:29:02.165655	981.67	Entregado	La Quintana	\N
5	4	2026-01-25 23:01:55.238614	875.65	Entregado	Mi casita	\N
\.


--
-- Data for Name: productostienda_raw; Type: TABLE DATA; Schema: public; Owner: AdminTienda
--

COPY public.productostienda_raw (id, tipo, sku, nombre, marca, precio, stock, descripcion, especificaciones) FROM stdin;
1	Portátil	LAP-ACER-001	Acer Aspire 3 Ryzen 3	Acer	375.00	12	Acer Aspire 3 – Laptop – AMD Ryzen 3 7320U 8GB 128GB SSD – Tecnología Portátil – Portátiles – A315-24P	\N
2	Portátil	LAP-ACER-002	Acer Nitro V Intel i5 GPU	Acer	875.00	12	Acer Nitro V – Laptop – Intel Core i5-13420H RTX 4050 6GB 512GB SSD – Tecnología Portátil – Portátiles – ANV15-51	\N
8	Almacenamiento	SSD-ADA-095	Adata Legeng 700	ADATA	50.50	40	Ssd Adata Legend 700 256gb Pci-e 3.0 Nvme M.2 2280 / 2400-1800 Mb/s	\N
9	Almacenamiento	SSD-ADA-097	Adata Xpg Spectrix 	ADATA	94.45	26	Ssd Adata Xpg Spectrix 500gb S20g Pcie Gen3x4 M.2 2280 3d Nand 	\N
11	Procesador	CPU-AMD-057	AMD Am5 Ryzen 7 7700 	AMD	431.42	10	Procesador Amd Am5 Ryzen 7 7700 3.8ghz 8core 16hilos 8mb Cache Ddr5-128gb Wraith Prism 	\N
12	Procesador	CPU-AMD-068	AMD Am5 Ryzen 9 7900	AMD	488.05	17	Procesador Amd Am5 Ryzen 9 7900 3.7ghz 12core 24hilos 12mb Cache Ddr5-128gb no-cooler 	\N
13	Procesador	CPU-AMD-064	AMD Ryzen 5 5500 	AMD	154.19	30	Amd Ryzen 5 5500 Hexa-core (6 Core) 3.60 Ghz Processor – CPU – RZ-5500BOX – AMD 	\N
14	Procesador	CPU-AMD-056	AMD Ryzen 5 5600GT	AMD	211.20	28	AMD – Ryzen 5 5600GT – 3.9 GHZ – GHz 6-core 12 threads 16 MB ca – Componentes Informáticos 	\N
15	Procesador	CPU-AMD-053	AMD Ryzen 5 7600X	AMD	275.35	25	Amd Ryzen 5 7000 7600x Hexa-core (6 Core) 4.70 Ghz Processor – CPU – RZ-7600XBX – AMD 	\N
16	Procesador	CPU-AMD-065	AMD Ryzen 5 9000 9600x 	AMD	305.94	11	Amd Ryzen 5 9000 9600x Hexa-core (6 Core) 3.90 Ghz Processor – Retail Pack – CPU – RZ9600XWOF – AMD	\N
18	Procesador	CPU-AMD-067	AMD Ryzen 7 8000 8700F 	AMD	333.16	21	AMD Ryzen 7 8000 8700F Octa-core (8 Core) 4.10 GHz Processor – 16 MB L3 Cache – 8 MB L2 Cache – 64-bit Processing – 5 GHz Overclocking Speed – 4 nm – Socket AM5 No Graphics – 65 W – 16 Threads – AMD – Processor – Y937	\N
19	Procesador	CPU-AMD-066	Amd Ryzen 7 9000 9700x 	AMD	435.00	10	Amd Ryzen 7 9000 9700x Octa-core (8 Core) 3.80 Ghz Processor – Retail Pack – CPU – RZ9700XWOF – AMD 	\N
20	Procesador	CPU-AMD-059	AMD Ryzen 9 7000 7900x	AMD	535.75	10	Amd Ryzen 9 7000 7900x Dodeca-core (12 Core) 4.70 Ghz Processor – CPU – RZ-7900XBX – AMD 	\N
234	Raton	MOU-DEL-120	Mouse Dell Wm126	DELL	19.83	55	Mouse Dell Wm126 Inalámbrico Negro 1y 	\N
21	Procesador	CPU-AMD-069	AMD Ryzen 9 9000 (2nd Gen) 9950x3d 	AMD	995.32	5	Amd Ryzen 9 9000 (2nd Gen) 9950x3d Hexadeca-core (16 Core) 4.30 Ghz Processor – Box – CPU – RZ9950X3DB – AMD 	\N
22	Teclado	KEY-APP-117	Apple Imac Magic Keyboard 2	Apple	121.00	33	Apple Imac Magic Keyboard 2 Wireless Numpad Silver White	\N
23	Teclado	KEY-APP-115	Apple Ipad 8 Smart Keyboard 	Apple	20.00	26	Apple Ipad 8 Smart Keyboard Black Spanish	\N
24	Teclado	KEY-APP-116	Apple Magic Keyboard 2	Apple	53.61	29	Apple Magic Keyboard 2 Silver Canada Specs 	\N
25	Teclado	KEY-APP-119	Apple Magic Keyboard 2021 W/ 	Apple	243.56	18	Apple Magic Keyboard 2021 W/ Touch Id & Num Keypad – Us English	\N
26	Teclado	KEY-APP-118	Apple Magic Keyboard W/	Apple	179.22	20	Apple Magic Keyboard W/ Num Keypad – Danish – Space Gray	\N
27	Raton	MOU-APP-121	Apple Magic Mouse 1	APPLE	105.69	22	Apple Magic Mouse White Multi-touch Surface 	\N
28	Raton	MOU-APP-123	Apple Magic Mouse 2	APPLE	121.00	15	Apple Magic Mouse – Black Multi-touch Surface 2nd Gen Lighting	\N
30	Monitor	MON-APP-150	Apple Studio Display 1	Apple	2158.31	22	Apple Studio Display Standard Glass Tilt-adjustable Stand 	\N
31	Monitor	MON-APP-151	Apple Studio Display 2	Apple	2737.34	20	Apple Studio Display Standard Glass – Tilt And Height-adjustable Stand	\N
32	Monitor	MON-APP-152	Apple Studio Display 3	Apple	3235.94	15	Apple Studio Display – Nano-texture Glass – Tilt And Height-adjustable Stand	\N
218	Tablet	TAB-LEN-029	Lenovo Tab P12	Lenovo	499.00	12	Pantalla 12.7 pulg 3K 8GB RAM 256GB WiFi incluye Tab Pen Plus Storm Grey-ZACH00	\N
33	Smartwatch	SWA-APP-004	Apple Watch SE 1	Apple	421.00	20	Apple Watch SE 3 – Smart watch – Midnight – Tecnología Portátil – Relojes – MX343LL/A	\N
34	Smartwatch	SWA-APP-005	Apple Watch SE 2	Apple	397.00	12	Apple Watch SE 3 – Smart watch – Starlight – Tecnología Portátil – Relojes – MX323LL/A	\N
35	Smartwatch	SWA-APP-010	Apple Watch SE 3	Apple	385.00	18	Apple Watch SE 2 – Smart watch – Silver – Tecnología Portátil – Relojes – MNK23LL/A	\N
49	Monitor	MON-ASU-140	Asus Va249qg	Asus	270.06	34	Asus Va249qg 24″ Class Full Hd Gaming Led Monitor – 16:9 – Black– ASUS 	\N
10	Portátil	LAP-DELL-018	Alienware m18 Ryzen 9 GPU	Dell	3100.00	1	Alienware m18 – Laptop – AMD Ryzen 9 7945HX RTX 4080 12GB 1TB SSD – Tecnología Portátil – Portátiles – AM18-R9	\N
17	Procesador	CPU-AMD-051	AMD Ryzen 7 7800X3D	AMD	581.70	14	Amd Ryzen 7 7000 7800x3d Octa-core (8 Core) 4.20 Ghz Processor – CPU – 7800X3DBOX – AMD 	\N
29	Raton	MOU-APP-124	Apple Magic Mouse 3	APPLE	121.00	24	Apple Magic Mouse White Multi-touch Surface 2nd Gen Lighting	\N
5	MemoriaRAM	RAM-ADA-085	Adata Ddr5	ADATA	455.95	16	Dimm Adata 32gb Ddr5 4800 1.1v 	\N
43	Portátil	LAP-ASUS-011	Asus ROG Scar 18 Intel i9 Ultima	Asus	3850.00	2	Asus ROG Scar 18 – Laptop – Intel Core i9-14900HX RTX 4090 16GB 2TB SSD – Tecnología Portátil – Portátiles – G834JYR	\N
44	Portátil	LAP-ASUS-010	Asus ROG Strix G16 Intel i9 GPU	Asus	2450.00	4	Asus ROG Strix G16 – Laptop – Intel Core i9-14900HX RTX 4070 8GB 1TB SSD – Tecnología Portátil – Portátiles – G614JIR	\N
45	Monitor	MON-ASU-142	Asus Rog Strix Xg32wcs	Asus	574.05	21	Asus Rog Strix Xg32wcs 32″ Class Wqhd Curved Screen Gaming Lcd Monitor – 16:9 – ASUS 	\N
46	Portátil	LAP-ASUS-012	Asus TUF A15 Ryzen 5 GPU	Asus	899.00	15	Asus TUF A15 – Laptop – AMD Ryzen 5 7535HS RTX 3050 4GB 512GB SSD – Tecnología Portátil – Portátiles – FA506NC	\N
47	Portátil	LAP-ASUS-013	Asus TUF A15 Ryzen 7 GPU	Asus	1150.00	8	Asus TUF A15 – Laptop – AMD Ryzen 7 7735HS RTX 4060 8GB 1TB SSD – Tecnología Portátil – Portátiles – FA507NV	\N
48	Portátil	LAP-ASUS-009	Asus TUF F15 Intel i7 GPU	Asus	1250.00	8	Asus TUF F15 – Laptop – Intel Core i7-13620H RTX 4060 8GB 1TB SSD – Tecnología Portátil – Portátiles – FX507VV	\N
50	Monitor	MON-ASU-141	Asus Va279qg 	Asus	308.67	26	Asus Va279qg 27″ Class Full Hd Gaming Led Monitor – 16:9 – ASUS	\N
51	Portátil	LAP-ASUS-006	Asus Vivobook 15 Ryzen 5	Asus	560.00	22	Asus Vivobook 15 – Laptop – AMD Ryzen 5 7520U 8GB 512GB SSD – Tecnología Portátil – Portátiles – M1502YA	\N
52	Portátil	LAP-ASUS-003	Asus Vivobook Go Intel i3	Asus	399.00	10	Asus Vivobook Go – Laptop – Intel Core i3-N305 8GB 256GB SSD – Tecnología Portátil – Portátiles – E1504GA	\N
53	Portátil	LAP-ASUS-007	Asus Vivobook S 14 Ryzen 7	Asus	890.00	10	Asus Vivobook S 14 – Laptop – AMD Ryzen 7 8845HS 16GB 512GB SSD – Tecnología Portátil – Portátiles – M5406UA	\N
54	Portátil	LAP-ASUS-008	Asus Vivobook S 16 Ryzen 9	Asus	1250.00	4	Asus Vivobook S 16 – Laptop – AMD Ryzen 9 8945HS 16GB 1TB SSD – Tecnología Portátil – Portátiles – M5606UA	\N
55	Portátil	LAP-ASUS-004	Asus Zenbook 14 Intel i7	Asus	1150.00	8	Asus Zenbook 14 – Laptop – Intel Core i7-1360P 16GB 1TB SSD – Tecnología Portátil – Portátiles – UX3402VA	\N
56	Portátil	LAP-ASUS-005	Asus Zenbook Duo Intel Ultra 9	Asus	2350.00	3	Asus Zenbook Duo – Laptop – Intel Core Ultra 9 185H 32GB 2TB SSD – Tecnología Portátil – Portátiles – UX8406MA	\N
57	Portátil	LAP-ASUS-014	Asus Zephyrus G14 Ryzen 9 GPU	Asus	2150.00	4	Asus Zephyrus G14 – Laptop – AMD Ryzen 9 8945HS RTX 4070 8GB 1TB SSD – Tecnología Portátil – Portátiles – GA403UI	\N
58	MemoriaRAM	RAM-COR-073	Corsair Dominator Platinum Rgb  	Corsair	439.99	9	Dimm Corsair Dominator Platinum Rgb 16gb (2x8gb) Ddr4 4000mhz C16 Kit  	\N
106	Tablet	TAB-LEN-023	Galaxy Tab A9+ 5G	Samsung	305.99	32	Pantalla 11 pulg 4GB RAM 64GB 5G LTE Gris -SM-X218U	\N
73	MemoriaRAM	RAM-CRU-082	Crucial CT64G56C46S5 	Crucial	864.89	10	Crucial Crucial CT64G56C46S5 64G DDR5 5600Mhz SODIMM Retail ME 295407 	\N
74	Almacenamiento	SSD-CRU-100	Crucial P310 1	Crucial	137.85	24	Crucial P310 500 Gb Solid State Drive – M.2 2280 Internal – Pci Express Nvme (pci Express Nvme 4.0 X4) – Notebook, Handheld Gaming Console Device Supported – 110 Tb Tbw – 1 – SSD – CRUCIAL	\N
75	Almacenamiento	SSD-CRU-102	Crucial P310 2	Crucial	321.68	20	Crucial P310 2 Tb Solid State Drive – M.2 2280 Internal – Pci Express Nvme (pci Express Nvme 4.0 X4) – SSD – CRUCIAL 	\N
76	Almacenamiento	SSD-CRU-104	Crucial P310 3	Crucial	536.15	8	Crucial P310 4 Tb Solid State Drive – M.2 2280 Internal – Pci Express Nvme (pci Express Nvme 4.0 X4) – SSD – CRUCIAL 	\N
77	MemoriaRAM	RAM-CRU-076	Crucial Pro Overclocking 1	Crucial	1128.94	5	Crucial Pro Overclocking Ram Module – MEM – CP6UX64WOC – CRUCIAL	\N
78	MemoriaRAM	RAM-CRU-081	Crucial Pro Overclocking 2	Crucial	503.98	26	Crucial Pro Overclocking Ram Module – MEM – CP64U32BOC – CRUCIAL 	\N
79	Almacenamiento	SSD-CRU-103	Crucial T700 	Crucial	375.30	12	Crucial T700 2 Tb Solid State Drive – M.2 2280 Internal – Pci Express Nvme (pci Express Nvme 5.0 X4) – SSD – CRUCIAL 	\N
80	Almacenamiento	SSD-CRU-101	Crucial T705	Crucial	277.26	19	Crucial T705 1 Tb Solid State Drive – M.2 2280 Internal – Pci Express Nvme (pci Express Nvme 5.0 X4) – SSD  – CRUCIAL 	\N
81	MemoriaRAM	RAM-DEL-074	Dell AB634642	Dell	468.99	10	Dell AB634642 – DDR4 SDRAM – 32 GB – 3200 MHz – System specific 	\N
82	Monitor	MON-DEL-145	Dell Alienware 34 	Dell	1278.53	18	Dell Alienware 34 Curved Gaming Monitor 34.1″ (3440×1440) 120hz Ips 2ms 350cd/m² 1900r Nvidia G-sync 1000:1 21:9 100-240vac 50/60hz Silver/black	\N
83	MemoriaRAM	RAM-DEL-089	Dell Ddr4 Rdimm	Dell	840.00	10	Dimm Dell 64gb Ddr4 Rdimm 3200mhz Ecc Para R450 R550 R650xs R750xs 	\N
84	MemoriaRAM	RAM-DEL-072	Dell Ddr5 Udimm 1	Dell	306.27	23	Dimm Dell 16gb Ddr5 Udimm 4800mhz Ecc Para R660xs R760xs 	\N
85	MemoriaRAM	RAM-DEL-088	Dell Ddr5 Udimm 2	Dell	557.30	21	Dimm Dell 32gb Ddr5 Udimm 4800mhz Ecc Para R660xs R760xs 	\N
86	Portátil	LAP-DELL-017	Dell G15 5530 Intel i7 GPU	Dell	1320.00	5	Dell G15 5530 – Laptop – Intel Core i7-13650HX RTX 4060 8GB 512GB SSD – Tecnología Portátil – Portátiles – G15-5530_i7	\N
87	Portátil	LAP-DELL-015	Dell Inspiron 3530 Intel i5	Dell	650.00	18	Dell Inspiron 3530 – Laptop – Intel Core i5-1335U 8GB 512GB SSD – Tecnología Portátil – Portátiles – i3530_i5	\N
88	Portátil	LAP-DELL-016	Dell Latitude 5440 Intel i7	Dell	1280.00	10	Dell Latitude 5440 – Laptop – Intel Core i7-1355U 16GB 512GB SSD – Tecnología Portátil – Portátiles – L5440_i7	\N
89	Monitor	MON-DEL-144	Dell P2725H	Dell	409.65	22	Dell P2725H – LCD monitor – 27″ – 1920 x 1080 – IPS – DisplayPort / HDMI / VGA / USB – Height adjustment.- Black 	\N
90	Monitor	MON-DEL-143	Dell Pro 22 Plus	Dell	320.21	30	Monitor Dell Pro 22 Plus P2225h 21.5inch 1920×1080 Ips Hdmi Dp Vga Usb-b 3xusb-a Usb-c 3y	\N
92	Celular	CEL-SAM-020	Galaxy A06	Samsung	145.00	42	Samsung Galaxy A06 4GB 128GB LTE Black - SM-A065FZK	\N
93	Celular	CEL-SAM-019	Galaxy A16	Samsung	225.40	38	Samsung Galaxy A16 6GB 128GB 5G Light Green - SM-A166BLG	\N
94	Celular	CEL-SAM-021	Galaxy A25	Samsung	265.00	43	Samsung Galaxy A25 6GB 128GB 5G Blue Black - SM-A256EZK	\N
95	Celular	CEL-SAM-018	Galaxy A36	Samsung	365.20	35	Samsung Galaxy A36 8GB 256GB 5G Awesome Navy - SM-A366BZA	\N
96	Celular	CEL-SAM-017	Galaxy A56 	Samsung	475.00	49	Samsung Galaxy A56 12GB 256GB 5G Awesome Graphite - SM-A566EZK	\N
97	Celular	CEL-SAM-016	Galaxy S24 	Samsung	645.00	26	Samsung Galaxy S24 FE 8GB 256GB 5G Blue - SM-S721BLB	\N
98	Celular	CEL-SAM-013	Galaxy S25 Plus	Samsung	1145.50	50	Samsung Galaxy S25 Plus 12GB 256GB 5G Cobalt Violet  - SM-S936BZAITPA	\N
99	Celular	CEL-SAM-012	Galaxy S25 ultra	Samsung	1383.36	45	Samsung Galaxy S25 Ultra 12GB 512GB 5G Titanium Black  - SM-S938BZKKTPA	\N
100	Tablet	TAB-SAM-021	Galaxy Tab A11 LTE	Samsung	249.78	20	Pantalla 8.7 pulg 4GB RAM 64GB 4G-LTE Gray-SM-X135GZA	\N
101	Tablet	TAB-SAM-019	Galaxy Tab A11 Plus LTE	Samsung	375.00	14	Pantalla 11 pulg 6GB RAM 128GB LTE Android 16 Gray-SM-X236BZA	\N
102	Tablet	TAB-SAM-020	Galaxy Tab A11 Plus WiFi	Samsung	303.60	21	Pantalla 11 pulg 6GB RAM 128GB WiFi Gray-SM-X230NZA	\N
103	Tablet	TAB-LEN-022	Galaxy Tab A11 WiFi	Samsung	195.96	20	Pantalla 8.7 pulg 4GB RAM 64GB WiFi G-ray-SM-X133NZA	\N
104	Tablet	TAB-LEN-024	Galaxy Tab A9 LTE	Samsung	195.00	10	Pantalla 8.7 pulg 4GB RAM 64GB LTE Graphite-SM-X115NZA	\N
105	Tablet	TAB-LEN-025	Galaxy Tab A9 WiFi	Samsung	123.18	12	Pantalla 8.7 pulg 4GB RAM 64GB WiFi Graphite-SM-X110NZA	\N
107	Tablet	TAB-SAM-018	Galaxy Tab Active 5	Samsung	743.11	15	Rugged 8 pulg 128GB LTE Grado Militar Resistente-SM-X306BZA	\N
108	Tablet	TAB-SAM-015	Galaxy Tab S10 FE 1	Samsung	669.18	29	Pantalla 13.1 pulg 8GB RAM 128GB WiFi Gris-SM-X620NZA	\N
109	Tablet	TAB-SAM-016	Galaxy Tab S10 FE 2	Samsung	667.90	21	Pantalla 10.9 pulg 8GB RAM 128GB 5G-LTE S-Pen Incluido-SM-X526BZA	\N
110	Tablet	TAB-SAM-017	Galaxy Tab S10 FE 3	Samsung	555.43	20	Pantalla 10.9 pulg 8GB RAM 128GB WiFi con Book Cover-SM-X520NZA	\N
111	Tablet	TAB-SAM-014	Galaxy Tab S10 FE+ 5G	Samsung	781.60	24	Pantalla 13.1 pulg 8GB RAM 128GB 5G-LTE Incluye Case-SM-X626BZA	\N
112	Tablet	TAB-SAM-013	Galaxy Tab S10+	Samsung	1079.00	26	Pantalla 12.4 pulg 12GB RAM 256GB WiFi Gris -SM-X820NZADGTO	\N
113	Tablet	TAB-SAM-011	Galaxy Tab S11 Ultra	Samsung	1295.50	20	Pantalla 14.6 pulg 12GB RAM 512GB 5G con S-Pen Gris-SM-X930NZA	\N
114	Tablet	TAB-SAM-012	Galaxy Tab S11 WiFi	Samsung	939.00	23	Pantalla 12.4 pulg 12GB RAM 256GB WiFi con Keyboard Cover-SM-X920NZA	\N
115	Celular	CEL-SAM-015	Galaxy Z Flip7	Samsung	1119.10	51	Samsung Galaxy Z Flip7 5G 12GB 256GB Mint - SM-F766BZKJGTO	\N
116	Celular	CEL-SAM-014	Galaxy Z Fold7	Samsung	1899.00	46	Samsung Galaxy Z Fold7 5G 12GB 512GB Phantom Black  - SM-F966BZA	\N
117	Celular	CEL-INFX-053	GT 30 Pro	Infinix	440.00	20	Infinix GT 30 Pro 5G 12GB 512GB Edición Dark Flare Gaming - GT30P-DF	\N
118	Celular	CEL-HON-046	Honor 200	Honor	520.00	10	Honor 200 5G 8GB 256GB Pantalla Curva 120Hz Moonlight White - ELI-NX9	\N
119	Celular	CEL-HON-047	Honor 200 Lite	Honor	295.00	14	Honor 200 Lite 8GB 256GB Cámara 108MP Ultra Delgado Starry Blue - LLY-NX1	\N
120	Celular	CEL-HON-045	Honor 200 Pro	Honor	745.00	20	Honor 200 Pro 12GB 512GB Retrato Studio Harcourt Ocean Cyan - ELP-NX9	\N
121	Celular	CEL-HON-044	Honor Magic V3	Honor	1850.00	54	Honor Magic V3 Plegable 12GB 512GB Ultra Delgado 9.2mm Green - FCP-LX9	\N
122	Celular	CEL-HON-043	Honor Magic7	Honor	950.00	12	Honor Magic7 5G 12GB 256GB Pantalla LTPO OLED 120Hz White - PXP-02	\N
123	Celular	CEL-HON-042	Honor Magic7 Pro	Honor	1199.00	34	Honor Magic7 Pro 5G 12GB 512GB Cámara Falcon H9000 Black - PXP-01	\N
124	Celular	CEL-HON-051	Honor X6c	Honor	165.00	20	Honor X6c 4GB 128GB Cámara Triple 50MP Ocean Blue - VNE-LX3	\N
125	Celular	CEL-HON-050	Honor X7c	Honor	215.00	2	Honor X7c 6GB 128GB Sonido Estéreo 200% Volúmen Forest Green - CMA-LX2	\N
126	Celular	CEL-HON-049	Honor X8c	Honor	265.00	15	Honor X8c 8GB 256GB Diseño Elegante Batería 5200mAh Midnight Black - CLK-LX2	\N
127	Celular	CEL-HON-048	Honor X9c 5G	Honor	385.00	5	Honor X9c 5G 12GB 256GB Ultra Resistente Pantalla Anti-caídas Titanium - BRP-LX1	\N
128	Celular	CEL-INFX-059	Hot 40 Pro	Infinix	185.00	20	Infinix Hot 40 Pro 16GB (8+8) 256GB 120Hz Palm Blue - X6837	\N
129	Celular	CEL-INFX-056	Hot 50 Pro Plus	Infinix	215.00	33	Infinix Hot 50 Pro Plus 8GB 256GB Helio G100 Sleek Black - X6880	\N
130	Celular	CEL-INFX-055	Hot 60 Pro	Infinix	231.00	31	Infinix Hot 60 Pro 8GB 256GB Pantalla 120Hz Sleek Black - H60P-BLK	\N
131	Portátil	LAP-HP-022	HP 15-ef Ryzen 3	HP	395.00	20	HP 15-ef – Laptop – AMD Ryzen 3 7320U 8GB 256GB SSD – Tecnología Portátil – Portátiles – 7P3Q1LA	\N
132	Portátil	LAP-HP-019	HP 250 G10 Intel i3	HP	425.00	15	HP 250 G10 – Laptop – Intel Core i3-1315U 8GB 256GB SSD – Tecnología Portátil – Portátiles – 7P3L3LA	\N
133	Monitor	MON-HP-146	Hp 527sf 	HP	340.83	25	Hp 527sf Monitor 27″ (1920×1080) 100hz Ips 5ms (gtg) 300 Cd/m² 1500:1 2xhdmi (1.4) 16:9 Tilt Adjustable Black / Silver	\N
134	Portátil	LAP-HP-021	HP Envy x360 Intel i7	HP	1090.00	5	HP Envy x360 – Laptop – Intel Core i7-1355U 16GB 512GB SSD – Tecnología Portátil – Portátiles – 7H9Y4LA	\N
135	Portátil	LAP-HP-023	HP Laptop 15 Ryzen 5	HP	585.00	18	HP Laptop 15 – Laptop – AMD Ryzen 5 5500U 16GB 512GB SSD – Tecnología Portátil – Portátiles – 802C3LA	\N
136	Portátil	LAP-HP-026	HP Omen 16 Intel i9 GPU	HP	2100.00	3	HP Omen 16 – Laptop – Intel Core i9-13900HX RTX 4070 8GB 1TB SSD – Tecnología Portátil – Portátiles – 802F5LA	\N
137	Portátil	LAP-HP-028	HP Omen 16 Ryzen 7 GPU	HP	1350.00	5	HP Omen 16 – Laptop – AMD Ryzen 7 7840H RTX 4060 8GB 512GB SSD – Tecnología Portátil – Portátiles – 802H2LA	\N
138	Portátil	LAP-HP-020	HP Pavilion 15 Intel i5	HP	685.00	20	HP Pavilion 15 – Laptop – Intel Core i5-1335U 16GB 512GB SSD – Tecnología Portátil – Portátiles – 802C4LA	\N
139	Portátil	LAP-HP-024	HP Pavilion Plus Ryzen 7	HP	920.00	8	HP Pavilion Plus – Laptop – AMD Ryzen 7 7840H 16GB 1TB SSD – Tecnología Portátil – Portátiles – 802D1LA	\N
140	Portátil	LAP-HP-025	HP Victus 15 Intel i5 GPU	HP	890.00	15	HP Victus 15 – Laptop – Intel Core i5-13420H RTX 3050 6GB 512GB SSD – Tecnología Portátil – Portátiles – 802E3LA	\N
141	Portátil	LAP-HP-027	HP Victus 15 Ryzen 5 GPU	HP	840.00	12	HP Victus 15 – Laptop – AMD Ryzen 5 7535HS RTX 2050 4GB 512GB SSD – Tecnología Portátil – Portátiles – 802G1LA	\N
198	Teclado	KEY-LEN-112	Lenovo Calliope	Lenovo	10.72	48	Lenovo Calliope Black External Usb Wired Desktop Uk Keyboard 00xh625	\N
152	Procesador	CPU-INT-055	Intel Core i3 (12th Gen) I3-12100f 	Intel	115.65	12	Intel Core I3 (12th Gen) I3-12100f Quad-core (4 Core) 3.30 Ghz Processor – Retail Pack – CPU – I3-12100FB – INTEL 	\N
153	Procesador	CPU-INT-061	Intel Core i3 14100 (14va) 	Intel	154.85	18	Procesador Intel Core I3 14100 (14va) 3.5 Ghz 12mb Lga1700	\N
154	Procesador	CPU-INT-058	Intel Core i3-13100	Intel	158.00	50	Procesador Intel Core I3 13100 (13 Va)	\N
155	Procesador	CPU-INT-052	Intel Core I5 13400 (13va)	Intel	260.50	30	Procesador Intel Core I5 13400 (13va) Con Video – Bx8071513400	\N
156	Procesador	CPU-INT-060	Intel Core i5-10400t 10gen	Intel	125.99	22	Procesador Intel Core I5-10400t 10gen 2.00-3.60ghz 6nucleos 12hilos 12mb Ddr4-2666 Uhd630 Tray Lga1200 	\N
157	Procesador	CPU-INT-063	Intel Core i7 12700 (12va)	Intel	371.10	16	Procesador Intel Core I7 12700 (12va) 2.10-4.90ghz 25mb Lga1700 *con Video* 	\N
158	Procesador	CPU-INT-054	Intel Core i7-14700K	Intel	531.91	18	Procesador Intel Core I7-14700k 14gen 3.40-5.50ghz 20n 28h 33mb Ddr5-5600 Uhd770 Novent Lga1700 	\N
159	Procesador	CPU-INT-050	Intel Core i9-14900K	Intel	688.00	20	Intel Core I9 (14th Gen) I9-14900k Tetracosa-core (24 Core) 3.20 Ghz Processor – Retail Pack – CPU – I9-14900KB – INTEL	\N
160	Procesador	CPU-INT-062	Intel Ultra 5 225	Intel	235.46	11	Procesador Intel Ultra 5 225 3.5hz 6-core 10-nucleos Cache 20mb Ddr5-6400 Vid Lga1851 	\N
161	Tablet	TAB-APP-001	iPad 10.2" (9na Gen) 1	Apple	349.00	15	iPad 10.2 pulgadas 64GB Wi-Fi Gris Espacial - MK2K3LL/A	\N
162	Tablet	TAB-APP-002	iPad 10.2" (9na Gen) 2	Apple	349.00	20	iPad 10.2 pulgadas 64GB Wi-Fi Plata - MK2L3LL/A	\N
163	Tablet	TAB-APP-003	iPad 10.9" (10ma Gen) 1	Apple	425.00	25	iPad 10.9 pulgadas 64GB Wi-Fi Blue (Azul) - MPQ13LL/A	\N
164	Tablet	TAB-APP-004	iPad 10.9" (10ma Gen) 2	Apple	425.00	31	iPad 10.9 pulgadas 64GB Wi-Fi Pink (Rosa) - MPQC3LL/A	\N
165	Tablet	TAB-APP-006	iPad Air 11" (M2) 1	Apple	715.00	24	iPad Air 11 pulgadas Chip M2 128GB Wi-Fi Space Gray - MUWC3LL/A	\N
166	Tablet	TAB-APP-007	iPad Air 11" (M2) 2	Apple	715.00	21	iPad Air 11 pulgadas Chip M2 128GB Wi-Fi Starlight - MUWD3LL/A	\N
167	Tablet	TAB-APP-008	iPad Air 13" (M2) 1	Apple	920.00	28	iPad Air 13 pulgadas Chip M2 128GB Wi-Fi Space Gray - MV233LL/A	\N
169	Tablet	TAB-APP-005	iPad mini 6	Apple	565.00	26	iPad mini 6ta Gen 64GB Wi-Fi Space Gray (Gris Espacial) - MK7M3LL/A	\N
170	Tablet	TAB-APP-010	iPad Pro 11" (M4)	Apple	1150.00	18	iPad Pro 11 pulgadas Chip M4 256GB Wi-Fi Space Black - MVX23LL/A	\N
171	Celular	CEL-APP-001	Iphone 15 	Apple	637.80	42	Apple Iphone 15 128gb Unlocked Tested Handset Only Green – Renewed Grade B – 15128GREEN-B	\N
172	Celular	CEL-APP-002	Iphone 15 plus	Apple	689.71	28	Apple Iphone 15 Plus 128gb Unlocked Tested Handset Only Green – Renewed Grade B – 15P128GREEN-B	\N
173	Celular	CEL-APP-005	Iphone 15 pro	Apple	900.70	24	Apple Iphone 15 Pro 256gb Unlocked Tested Handset Only Black – Renewed Grade B – 15PRO256B-B	\N
168	Tablet	TAB-APP-009	iPad Air 13" (M2) 2	Apple	921.52	14	iPad Air 13 pulgadas Chip M2 128GB Wi-Fi Blue (Azul) - MV243LL/A	\N
174	Celular	CEL-APP-007	Iphone 15 pro max	Apple	1092.52	21	Apple Iphone 15 Pro Max 512gb Unlocked Tested Handset Only Blue – Renewed Grade B – 15PROM512BLUE-B	\N
175	Celular	CEL-APP-003	Iphone 16 	Apple	796.85	26	Apple Iphone 16 128gb Unlocked Tested Handset Only Black – Renewed Grade B – 16128B-B	\N
176	Celular	CEL-APP-006	Iphone 16 plus	Apple	1051.00	18	Apple Iphone 16 Plus 512gb White Mixed Versions, L1 Tested	\N
177	Celular	CEL-APP-008	Iphone 16 pro max	Apple	1418.25	31	Apple Iphone 16 Pro Max 256gb Unlocked Tested Handset Only White – Renewed Grade B – 16PROM512W-B	\N
178	Celular	CEL-APP-004	Iphone 16e	Apple	1007.40	40	Apple iPhone 16e – Smartphone – iOS – Black – Touch – 128GB – Celulares – MD1Q4BE/A	\N
179	Celular	CEL-APP-009	Iphone 17 Air	Apple	1171.62	24	Celular Apple Iphone 17 Air 256gb Light Gold A3260 – Mg1a4ll/a	\N
180	Celular	CEL-APP-011	Iphone 17 pro 	Apple	1723.45	30	Celular Apple Iphone 17 Pro Max 256gb Deep Blue – Esim (homologado)	\N
181	Celular	CEL-APP-010	Iphone 17 pro max	Apple	1249.12	29	Apple Iphone 17 Pro Max 256gb Unlocked Tested Handset Only Black – Renewed Grade A – 17PM256B-A	\N
182	Almacenamiento	HDD-KIN-093	Kingston Dc600m 	Kingston	298.78	23	Hdd Kingston Dc600m 960gb Sata Ssds 2.5inch 3d Tlc Sata Iii – SEDC600M/960G	\N
183	MemoriaRAM	RAM-KIN-077	Kingston DDR3-1600	Kingston	32.00	38	Kingston 4GB DDR3-1600 1.35v SODIMM 	\N
184	MemoriaRAM	RAM-KIN-071	Kingston Fury Beast Dr5	Kingston	245.00	16	Kingston Fury Beast 16gb Ddr5 Sdram Memory Module – MEM – KF552BB-16 – KINGSTON 	\N
185	Almacenamiento	SSD-KIN-091	Kingston KC600	Kingston	81.96	37	Kingston KC600 – SSD – cifrado – 256 GB – interno – 2.5″ – SATA 6Gb/s – AES de 256 bits – TCG Opal Encryption, Self-Encrypting Drive (SED) – SKC600/256G	\N
186	MemoriaRAM	RAM-KIN-079	Kingston Memory KVR32N22S8 16 	Kingston	160.30	14	Kingston Kingston Memory KVR32N22S8 16 16GB 3200MHz DDR4 Non-ECC CL22 DIMM 1Rx8 Retail ME 246102 	\N
187	Almacenamiento	SSD-KIN-090	Kingston NV2 	Kingston	70.00	25	1TB SSD M.2 NVME KINGSTON NV2 3500MB/S PCIE 4.0	\N
188	Almacenamiento	SSD-KIN-092	Kingston Nv3	Kingston	122.43	29	Ssd/kingston 1tb Nv3 M.2 2280 Nvme Ssd Up To 6000 Mb/s – SNV3S/1000G	\N
189	MemoriaRAM	RAM-KIN-078	Kingston Valueram 	Kingston	71.95	29	Kingston Valueram 8gb Ddr3 Sdram Memory Module – MEM – W8GSO16LSK – KINGSTON 	\N
190	MemoriaRAM	RAM-KIN-070	Kingston Valueram Ddr4	Kingston	94.96	30	Kingston Valueram 8gb Ddr4 Sdram Memory Module – MEM – F32UA8GK – KINGSTON 	\N
191	Teclado	KEY-LEN-113	Lenovo (lecoo) Gk301	Lenovo	23.00	54	Lenovo (lecoo) Gk301 Wired Mechanical Gaming Keyboard Usb Wired Rgb Backlight – Gun Metal – (english) – Moq 20 Pcs. Per Master 	\N
192	Teclado	KEY-LEN-111	Lenovo (lecoo) Kb103	Lenovo	9.19	50	Lenovo (lecoo) Kb103, Membrane Usb Wired Keyboard, Black (spanish) – Moq 20 Pcs. Per Master 	\N
193	Raton	MOU-LEN-125	Lenovo (lecoo) Ms102 	Lenovo	6.13	45	Lenovo (lecoo) Ms102 Wired Mouse 2400 Max Dpi, 4 Keys, Black – Moq 100 Pcs. Per Master	\N
194	Raton	MOU-LEN-126	Lenovo (lecoo) Ms103	Lenovo	6.13	40	Lenovo (lecoo) Ms103 Usb Wired Mouse 1000 Dpi, 3 Keys, Black – Moq 100 Pcs. Per Master 	\N
195	Raton	MOU-LEN-127	Lenovo (lecoo) Ms104	Lenovo	7.66	32	Lenovo (lecoo) Ms104 Wired Mouse 1600 Max Dpi, 3 Million Clicks, Black – Moq 100 Pcs. Per Master 	\N
196	Raton	MOU-LEN-128	Lenovo (lecoo) Ms108	Lenovo	9.19	30	Lenovo (lecoo) Ms108 Wired Gaming Mouse 6400 Max Dpi, 7 Keys, Black – Moq 40 Pcs. Per Master 	\N
197	Teclado	KEY-LEN-114	Lenovo 0b47190	Lenovo	29.10	37	Lenovo 0b47190 Thinkpad Compact Usb Keyboard With Trackpoint – Us English 	\N
199	Portátil	LAP-LEN-031	Lenovo IdeaPad 3 Ryzen 3	Lenovo	380.00	15	Lenovo IdeaPad 3 – Laptop – AMD Ryzen 3 5300U 8GB 512GB SSD – Tecnología Portátil – Portátiles – 82KU01X0US	\N
200	Portátil	LAP-LEN-035	Lenovo IdeaPad Gaming 3 Ryzen 5 GPU	Lenovo	820.00	10	Lenovo IdeaPad Gaming 3 – Laptop – AMD Ryzen 5 5600H RTX 3050 4GB 512GB SSD – Tecnología Portátil – Portátiles – 82K201XCUS	\N
201	Portátil	LAP-LEN-030	Lenovo IdeaPad Slim 3 Intel i5	Lenovo	620.00	25	Lenovo IdeaPad Slim 3 – Laptop – Intel Core i5-12450H 8GB 512GB SSD – Tecnología Portátil – Portátiles – 82RK00BEUS	\N
202	Portátil	LAP-LEN-033	Lenovo IdeaPad Slim 5 Ryzen 7	Lenovo	870.00	12	Lenovo IdeaPad Slim 5 – Laptop – AMD Ryzen 7 7730U 16GB 512GB SSD – Tecnología Portátil – Portátiles – 82XF0013US	\N
203	Monitor	MON-LEN-149	Lenovo L15 	Lenovo	316.71	10	Lenovo L15 Portable Monitor 15.6″ (1920×1080) Ips 250 Cd/m² 1000:1 2xusb2.0 2xusb-c 16:9 Raven Black	\N
204	Monitor	MON-LEN-147	Lenovo Legion Gaming R25i-30 	Lenovo	358.53	20	Lenovo Legion Gaming R25i-30 24.5″ (1920×1080) 180hz Monitor	\N
205	Portátil	LAP-LEN-037	Lenovo Legion Pro 7 Ryzen 9 GPU	Lenovo	2850.00	3	Lenovo Legion Pro 7 – Laptop – AMD Ryzen 9 7945HX RTX 4080 12GB 1TB SSD – Tecnología Portátil – Portátiles – 82WS003UUS	\N
206	Portátil	LAP-LEN-036	Lenovo Legion Slim 5 Ryzen 7 GPU	Lenovo	1280.00	6	Lenovo Legion Slim 5 – Laptop – AMD Ryzen 7 7840HS RTX 4060 8GB 512GB SSD – Tecnología Portátil – Portátiles – 82Y9000NUS	\N
207	Tablet	TAB-LEN-034	Lenovo Legion Tab	Lenovo	550.00	8	Pantalla 8.8 pulg QHD 12GB RAM 256GB WiFi Storm Grey gaming de alto rendimiento-ZACW00	\N
208	Raton	MOU-LEN-129	Lenovo Logitech G G502 X	Lenovo	75.10	28	Lenovo Logitech G G502 X Lightforce Wired Gaming Mouse White 	\N
209	Portátil	LAP-LEN-034	Lenovo LOQ 15 Intel i5 GPU	Lenovo	860.00	10	Lenovo LOQ 15 – Laptop – Intel Core i5-12450H RTX 3050 6GB 512GB SSD – Tecnología Portátil – Portátiles – 82XV00L9US	\N
210	Tablet	TAB-LEN-032	Lenovo Tab K10	Lenovo	265.00	15	Pantalla 10.3 pulg 4GB RAM 64GB WiFi Abyss Blue uso empresarial y educativo-ZA8N00	\N
211	Tablet	TAB-LEN-027	Lenovo Tab M10 Gen 3	Lenovo	210.00	35	Pantalla 10.1 pulg 4GB RAM 64GB WiFi Storm Grey Procesador Octa Core-ZAAF00	\N
212	Tablet	TAB-LEN-033	Lenovo Tab M10 Plus Gen 3	Lenovo	285.00	25	Pantalla 10.6 pulg 2K 4GB RAM 128GB WiFi Storm Grey cuatro altavoces Dolby Atmos-ZAAJ00	\N
213	Tablet	TAB-LEN-026	Lenovo Tab M11	Lenovo	245.00	21	Pantalla 11 pulg 4GB RAM 128GB WiFi incluye Lenovo Tab Pen Luna Grey-ZADA00	\N
214	Tablet	TAB-LEN-031	Lenovo Tab M8 Gen 4	Lenovo	145.00	17	Pantalla 8 pulg 2GB RAM 32GB WiFi Arctic Grey diseño compacto para niños-ZABU00	\N
216	Tablet	TAB-LEN-028	Lenovo Tab P11 Gen 2	Lenovo	385.00	20	Pantalla 11.5 pulg 2K 6GB RAM 128GB WiFi Storm Grey compatible con teclado-ZABW00	\N
217	Tablet	TAB-LEN-035	Lenovo Tab P11 Pro Gen 2	Lenovo	525.00	10	Pantalla 11.2 pulg OLED 120Hz 8GB RAM 256GB WiFi Storm Grey experiencia premium-ZAB500	\N
219	Monitor	MON-LEN-148	Lenovo Thinkcentre Tiny-in-one 24	Lenovo	546.71	19	Lenovo Thinkcentre Tiny-in-one 24 Gen 5 Monitor 23.8″ (1920×1080) Touchscreen Ips 4ms 250 Cd/m² 1000:1 16:9 Adjustable Stand Black	\N
220	Portátil	LAP-LEN-029	Lenovo V15 G4 Intel i3	Lenovo	410.00	12	Lenovo V15 G4 – Laptop – Intel Core i3-1315U 8GB 512GB SSD – Tecnología Portátil – Portátiles – 82YU006XLM	\N
221	Portátil	LAP-LEN-032	Lenovo V15 G4 Ryzen 5	Lenovo	545.00	20	Lenovo V15 G4 – Laptop – AMD Ryzen 5 7520U 8GB 512GB SSD – Tecnología Portátil – Portátiles – 82YU000WLM	\N
222	Celular	CEL-HUA-041	Mate 60 Pro	Huawei	945.00	25	Huawei Mate 60 Pro 12GB 512GB Conexión Satelital Verde - ALN-AL00	\N
223	MemoriaRAM	RAM-DEL-087	Memoria Dell Interna Dell Ac049361	Dell	512.96	15	Memoria Dell Interna Dell Ac049361 32gb-ddr5 Udimm 4800mt-ecc	\N
224	MemoriaRAM	RAM-CRU-080	Micron 	Crucial	186.89	31	Micron 16gb Ddr4 Sdram Memory Module – MEM – CRUCIAL 	\N
225	Almacenamiento	SSD-MIC-105	Micron 5400 Pro 1	Micron	288.00	21	Micron 5400 Pro 240 Gb Solid State Drive – M.2 2280 Internal – Sata (sata/600) – Read Intensive – SSD – R5400P240B – MICRON 	\N
226	Almacenamiento	SSD-MIC-106	Micron 5400 Pro 2	Micron	341.60	15	Micron 5400 Pro 480 Gb Solid State Drive – M.2 Internal – Sata (sata/600) – Read Intensive – SSD – R5400P480B – MICRON	\N
227	Almacenamiento	SSD-MIC-107	Micron 5400 Pro 3	Micron	479.45	11	Micron 5400 Pro 960 Gb Solid State Drive – M.2 2280 Internal – Sata (sata/600) – Read Intensive – SSD – R5400P960B – MICRON 	\N
228	Almacenamiento	SSD-MIC-108	Micron 7450 Pro 1	Micron	514.68	5	Micron 7450 Pro 960 Gb Solid State Drive – M.2 22110 Internal – Pci Express Nvme (pci Express Nvme 4.0) – Green – SSD – R7450P960C – MICRON 	\N
229	Almacenamiento	SSD-MIC-109	Micron 7450 Pro 2	Micron	507.03	7	Micron 7450 Pro 960 Gb Solid State Drive – M.2 22110 Internal – Pci Express Nvme (pci Express Nvme 4.0 X4) – Read Intensive – Taa Compliant – SSD – R7450P960I – MICRON 	\N
230	Monitor	MON-LG-155	Monitor Lg UltraFine	LG	696.70	23	Monitor Lg 31.5inch Uhd 4k Ergo Usb-c Hdmi Dp Soporte Ergonomico Con Abrazadera En C Amd Freesync	\N
231	Monitor	MON-LG-153	Monitor Lg UltraGear	LG	289.40	27	Monitor Lg 23.8pulg Gaming Flat Ultragear Full-hd Ips 1920×1080 Dp Hdmi 180hz 1ms G-sync Black	\N
232	Monitor	MON-LG-154	Monitor Lg UltraWide	LG	351.03	32	Monitor Lg 29inch Ultrawide Led Ips 2560×1080 Uwfhd 100hz 5ms Hdmi Dp Black	\N
235	Portátil	LAP-MSI-038	MSI Katana 15 Intel i7 GPU	MSI	1190.00	6	MSI Katana 15 – Laptop – Intel Core i7-13620H RTX 4060 8GB 512GB SSD – Tecnología Portátil – Portátiles – B13VGK	\N
236	Portátil	LAP-MSI-039	MSI Raider GE78 Intel i9 GPU	MSI	3200.00	2	MSI Raider GE78 – Laptop – Intel Core i9-14900HX RTX 4080 12GB 2TB SSD – Tecnología Portátil – Portátiles – HX14VHG	\N
237	Celular	CEL-INFX-058	Note 40	Infinix	192.00	36	Infinix Note 40 8GB 256GB Pantalla 6.7" 108MP Titan Gold - 1CINF5708	\N
238	Celular	CEL-INFX-057	Note 40 Pro	Infinix	394.99	32	Infinix Note 40 Pro 8GB 256GB Vintage Green Cargador Inalámbrico - 1CINF2225	\N
239	Celular	CEL-INFX-054	Note 50 Pro+	Infinix	394.99	35	Infinix Note 50 Pro+ 5G 8GB 256GB Carga Relámpago Titan Gold - 4894947019401	\N
240	Celular	CEL-HUA-037	Nova 11 Pro	Huawei	589.00	52	Huawei Nova 11 Pro 8GB 256GB Cuero Vegano Verde - GOA-LX9	\N
241	Celular	CEL-HUA-038	Nova 11i	Huawei	245.00	15	Huawei Nova 11i 8GB 128GB Pantalla FullView 6.8" Negro - MAO-LX9	\N
242	Celular	CEL-HUA-036	Nova 12 SE	Huawei	310.00	20	Huawei Nova 12 SE 8GB 256GB Diseño Ultra Fino Verde - BNE-LX3	\N
243	Celular	CEL-HUA-035	Nova 12s	Huawei	548.15	41	Huawei Nova 12s 8GB 256GB Pantalla OLED 6.7" Azul Orquídea - FOA-LX9	\N
244	Celular	CEL-HUA-040	Nova Y72	Huawei	228.00	14	Huawei Nova Y72 8GB 128GB Botón X Personalizable Negro - MGA-LX3	\N
245	Celular	CEL-HUA-039	Nova Y91	Huawei	342.10	48	Huawei Nova Y91 8GB 256GB Batería 7000mAh Plata - STG-LX3	\N
246	Celular	CEL-XIA-030	Poco F7 Pro	Xiaomi	699.00	26	Poco F7 Pro 5G 12GB 512GB High Performance Black - 24122RKC3G	\N
247	Celular	CEL-XIA-031	Poco X7 Pro 5G	Xiaomi	395.00	20	Poco X7 Pro 5G 8GB 256GB Gaming Edition Blue - 24095PC43G	\N
248	Celular	CEL-HUA-034	Pura 70	Huawei	895.40	29	Huawei Pura 70 12GB 256GB Cámara Súper Macro Negro Carbono - ADY-LX9	\N
249	Celular	CEL-HUA-033	Pura 70 Pro	Huawei	1150.00	24	Huawei Pura 70 Pro 12GB 512GB Cristal Kunlun Glass Blanco - P70P-WHT	\N
251	Raton	MOU-RAZ-131	Razer Basilisk V3	RAZER	67.70	19	Razer Basilisk V3 – Ratón – ergonómico – diestro – óptico – 11 botones – cableado – USB	\N
252	Raton	MOU-RAZ-134	Razer Basilisk V3 X Hyperspeed	RAZER	95.00	20	Razer Basilisk V3 X Hyperspeed – Ergonomic Wireless Gaming Mouse	\N
253	Portátil	LAP-RAZ-040	Razer Blade 14 Ryzen 9 Ultima	Razer	2650.00	2	Razer Blade 14 – Laptop – AMD Ryzen 9 8945HS RTX 4070 8GB 1TB SSD – Tecnología Portátil – Portátiles – RZ09-0508	\N
254	Raton	MOU-RAZ-130	Razer Deathadder Essential	RAZER	26.81	30	Razer Deathadder Essential – Ergonomic Wired Gaming Mouse – White Edition 	\N
255	Raton	MOU-RAZ-132	Razer Deathadder V3 	RAZER	84.25	25	Razer Deathadder V3 – Ergonomic Wired Gaming Mouse Esport – Sensor óptico 	\N
256	Raton	MOU-RAZ-133	Razer Naga V2 HyperSpeed 	RAZER	89.07	35	Razer Naga V2 HyperSpeed – Ratón – ergonómico – ratón gaming para MMO – diestro – óptico – 19 botones – inalámbrico – Bluetooth, 2.4 GHz – receptor inalámbrico USB	\N
257	Raton	MOU-RAZ-135	Razer Viper V3 Pro 	RAZER	193.00	15	Razer Viper V3 Pro – Wireless Esports Gaming Mouse (white Edition) 	\N
258	Celular	CEL-XIA-023	Redmi 13	Xiaomi	195.00	26	Xiaomi Redmi 13 8GB RAM 256GB 108MP Sandy Gold - RED13-SGOLD	\N
259	Celular	CEL-XIA-022	Redmi 14C	Xiaomi	165.00	21	Xiaomi Redmi 14C 4GB RAM 128GB 6.8" 50MP Azul - 1CXIA2325	\N
260	Celular	CEL-XIA-026	Redmi A3	Xiaomi	112.42	15	Celular Xiaomi Redmi A3 4GB RAM 128GB Midnight Black - REDA3-BLK	\N
261	Celular	CEL-XIA-027	Redmi Note 13 Pro	Xiaomi	312.48	18	Xiaomi Redmi Note 13 Pro 8GB 256GB 200MP Black - 6941812758915	\N
262	Celular	CEL-XIA-025	Redmi Note 14 Pro+	Xiaomi	545.00	24	Redmi Note 14 Pro Plus 5G 12GB 512GB Aurora Purple - 24090RA29G	\N
263	Raton	MOU-RED-139	Redragon Impact Elite M913	REDRAGON	72.00	24	Redragon Impact Elite M913 Gaming Mouse – REDRAGON 	\N
264	Raton	MOU-RED-136	Redragon Nirvana M652	REDRAGON	27.57	26	Redragon Nirvana M652 Gaming Mouse – REDRAGON 	\N
265	Raton	MOU-RED-137	Redragon Predator M612 	REDRAGON	36.76	40	Redragon Predator M612 Gaming Mouse – REDRAGON	\N
266	Raton	MOU-RED-138	Redragon Storm M808	REDRAGON	45.95	38	Redragon Storm M808 Gaming Mouse – REDRAGON 	\N
277	Monitor	MON-SAM-156	Samsung Monitor Essential S3	Samsung	227.52	34	Samsung Monitor 27″ Essential Curved-Full hd-1920×1080-75–100 Hz-250 cd/m²	\N
278	Monitor	MON-SAM-158	Samsung Odyssey Oled G6	Samsung	1270.48	6	Samsung Odyssey Oled G6 G60sd 27″ Qhd (2560 X 1440) 1440p 360 Hz Gaming Monitor	\N
279	Almacenamiento	SSD-SAM-094	Samsung Pm9b1	Samsung	113.35	34	Samsung Pm9b1 256 Gb M.2 Pcie 4.0 X4 (nvme) Ssd – SSD – PM9B1256G – SAMSUNGOEM 	\N
280	Celular	CEL-INFX-060	Smart 10	Infinix	122.00	14	Infinix Smart 10 4GB 128GB Pantalla 6.6" Midnight Black - S10-BLK	\N
281	Monitor	MON-SAM-157	Smart Monitor M7	Samsung	459.85	17	Samsung 32″ M70c Uhd Uhd 4k (3840 X 2160) 60hz White 	\N
284	Teclado	KEY-HP-110	Teclado Omen	HP	122.86	30	Teclado Omen By Hp Encoder Red Usb Black	\N
285	Celular	CEL-XIA-024	Xiaomi 14T Pro	Xiaomi	785.00	25	Xiaomi 14T Pro 12GB 512GB 5G Leica Titan Black - 2407FPN8EG	\N
286	Celular	CEL-XIA-029	Xiaomi 15	Xiaomi	890.25	27	Xiaomi 15 5G 12GB 256GB White Display 120Hz - 24123PX78L	\N
287	Celular	CEL-XIA-028	Xiaomi 15 Ultra	Xiaomi	1299.00	29	Xiaomi 15 Ultra 5G 16GB 512GB Leica Camera Black - 24124PX78G	\N
288	Celular	CEL-INFX-052	Zero 40 5G	Infinix	428.99	14	Infinix Zero 40 5G 12GB 512GB Pantalla AMOLED 144Hz Rock Black - 4894947021459	\N
3	MemoriaRAM	RAM-ADA-075	Adata Ddr4 1	ADATA	83.47	30	Dimm Adata 8gb 3200mhz Ddr4 L22 1.2v 	\N
4	MemoriaRAM	RAM-ADA-084	Adata Ddr4 2	ADATA	195.64	20	Dimm Adata 32gb Ddr4 3200mhz 1.2v 	\N
6	Almacenamiento	SSD-ADA-098	Adata Legend 860 1	ADATA	117.23	31	Ssd Adata Legend 860 1tb M.2 2280 3d Nand Pcie Gen4x4 5000mb-s 5y 	\N
7	Almacenamiento	SSD-ADA-099	Adata Legend 860 2	ADATA	223.61	16	Ssd Adata Legend 860 2tb M.2 2280 3d Nand Pcie Gen4x4 5000mb-s 5y 	\N
282	MemoriaRAM	RAM-ADA-083	So dimm Adata  1	ADATA	61.96	45	So-dimm Adata 8gb Pc-1600 Ddr3l Low Voltage 	\N
283	MemoriaRAM	RAM-ADA-086	So dimm Adata  2	ADATA	508.71	12	So-dimm Adata 32gb Ddr5-5600 1.1v L46 262pin 2800mhz 	\N
36	Smartwatch	SWA-APP-002	Apple Watch Series 1	Apple	638.00	10	Apple Watch Series 11 – Smart watch – Midnight – Tecnología Portátil – Relojes – MWX13LL/A	\N
37	Smartwatch	SWA-APP-003	Apple Watch Series 2	Apple	602.00	8	Apple Watch Series 11 – Smart watch – Silver – Tecnología Portátil – Relojes – MWX03LL/A	\N
38	Smartwatch	SWA-APP-007	Apple Watch Series 10 1	Apple	539.00	7	Apple Watch Series 10 – Smart watch – Rose Gold – Tecnología Portátil – Relojes – MWWH3LW/A	\N
39	Smartwatch	SWA-APP-006	Apple Watch Series 10 2	Apple	579.00	5	Apple Watch Series 10 – Smart watch – Jet Black – Tecnología Portátil – Relojes – MWWQ3LW/A	\N
40	Smartwatch	SWA-APP-009	Apple Watch Sport Loop 	Apple	68.00	25	Apple Watch Sport Loop – Smart watch – Cypress – Tecnología Portátil – Relojes – MT5G3AM/A	\N
41	Smartwatch	SWA-APP-001	Apple Watch Ultra 1	Apple	1117.70	15	Apple Watch Ultra 3 – Smart watch – Black Titanium – Tecnología Portátil – Relojes – MF0J4LW/A	\N
42	Smartwatch	SWA-APP-008	Apple Watch Ultra 2	Apple	919.00	10	Apple Watch Ultra 2 – Smart watch – Titanium – Tecnología Portátil – Relojes – MREK3LL/A	\N
142	Smartwatch	SWA-HUA--030	Huawei Band 10	Huawei	55.00	25	Huawei Band 10 – Smart watch – Black – Tecnología Portátil – Relojes – ASK-B19	\N
143	Smartwatch	SWA-HUA--029	Huawei Watch D2	Huawei	449.00	8	Huawei Watch D2 – Smart watch – White – Tecnología Portátil – Relojes – LCA-B1	\N
144	Smartwatch	SWA-HUA--027	Huawei Watch Fit 3	Huawei	135.00	30	Huawei Watch Fit 3 – Smart watch – Pink – Tecnología Portátil – Relojes – B19	\N
145	Smartwatch	SWA-HUA--026	Huawei Watch Fit 4	Huawei	169.00	25	Huawei Watch Fit 4 – Smart watch – Grey – Tecnología Portátil – Relojes – MIL-B09	\N
146	Smartwatch	SWA-HUA--025	Huawei Watch Fit 4 Pro	Huawei	235.00	18	Huawei Watch Fit 4 Pro – Smart watch – Blue – Tecnología Portátil – Relojes – MIL-B19	\N
147	Smartwatch	SWA-HUA--024	Huawei Watch GT 5 46mm	Huawei	215.00	20	Huawei Watch GT 5 – Smart watch – Black – Tecnología Portátil – Relojes – Jana-B19	\N
148	Smartwatch	SWA-HUA--023	Huawei Watch GT 5 Pro 46mm	Huawei	415.00	10	Huawei Watch GT 5 Pro – Smart watch – Titanium – Tecnología Portátil – Relojes – Vili-B19	\N
149	Smartwatch	SWA-HUA--022	Huawei Watch GT 6 41mm	Huawei	339.00	12	Huawei Watch GT 6 – Smart watch – Silver – Tecnología Portátil – Relojes – VPP-B29	\N
267	Smartwatch	SWA-SAM-019	Samsung Galaxy Watch 6 1	Samsung	249.00	7	Samsung Galaxy Watch 6 – Smart watch – Graphite – Tecnología Portátil – Relojes – SM-R940N	\N
268	Smartwatch	SWA-SAM-020	Samsung Galaxy Watch 6 2	Samsung	219.00	10	Samsung Galaxy Watch 6 – Smart watch – Gold – Tecnología Portátil – Relojes – SM-R930N	\N
150	Smartwatch	SWA-HUA--021	Huawei Watch GT 6 46mm	Huawei	339.00	15	Huawei Watch GT 6 – Smart watch – Black – Tecnología Portátil – Relojes – VPP-B19	\N
151	Smartwatch	SWA-HUA--028	Huawei Watch Ultimate	Huawei	999.00	5	Huawei Watch Ultimate – Smart watch – Blue – Tecnología Portátil – Relojes – CLB-B19	\N
269	Smartwatch	SWA-SAM-018	Samsung Galaxy Watch 6 Classic 	Samsung	389.00	5	Samsung Galaxy Watch 6 Classic – Smart watch – Black – Tecnología Portátil – Relojes – SM-R960N	\N
270	Smartwatch	SWA-SAM-013	Samsung Galaxy Watch 7  1	Samsung	329.00	15	Samsung Galaxy Watch 7 – Smart watch – Green – Tecnología Portátil – Relojes – SM-L310N	\N
271	Smartwatch	SWA-SAM-014	Samsung Galaxy Watch 7  2	Samsung	329.00	12	Samsung Galaxy Watch 7 – Smart watch – Silver – Tecnología Portátil – Relojes – SM-L310N	\N
272	Smartwatch	SWA-SAM-015	Samsung Galaxy Watch 7  3	Samsung	299.00	20	Samsung Galaxy Watch 7 – Smart watch – Cream – Tecnología Portátil – Relojes – SM-L300N	\N
273	Smartwatch	SWA-SAM-016	Samsung Galaxy Watch FE 1	Samsung	199.00	25	Samsung Galaxy Watch FE – Smart watch – Black – Tecnología Portátil – Relojes – SM-R861N	\N
274	Smartwatch	SWA-SAM-017	Samsung Galaxy Watch FE 2	Samsung	199.00	18	Samsung Galaxy Watch FE – Smart watch – Pink Gold – Tecnología Portátil – Relojes – SM-R861N	\N
275	Smartwatch	SWA-SAM-011	Samsung Galaxy Watch Ultra 1	Samsung	649.00	10	Samsung Galaxy Watch Ultra – Smart watch – Titanium Silver – Tecnología Portátil – Relojes – SM-L705F	\N
276	Smartwatch	SWA-SAM-012	Samsung Galaxy Watch Ultra 2	Samsung	649.00	8	Samsung Galaxy Watch Ultra – Smart watch – Titanium Blue– Tecnología Portátil – Relojes – SM-L705F	\N
59	Escritorio	DESK-INT-013	Cpu Desktop Intel I3	Intel	345.00	15	Cpu Desktop - Quasad C3156 - Intel Core i3 13100 (13va) - ASUS Prime H610M-E - Ram 8GB DDR4 - Ssd 256GB M.2 - Fuente 450w	\N
60	Escritorio	DESK-INT-015	Cpu Desktop Intel I5 1	Intel	540.00	12	Cpu Desktop - Antryx Xtreme E250 - Intel Core i5 12400 (12va) - MSI PRO B660M-A - Ram 16GB DDR4 - Ssd 512GB M.2 - Fuente 500w	\N
61	Escritorio	DESK-INT-001	Cpu Desktop Intel I5 2	Intel	370.00	7	Cpu Desktop- Infinytek Angel Warrior- Rgb- Intel Core I5 12400 (12va) -Asus H610mf- Ram 16gb DDR5 -Ssd512gb M.2- Fuente 550w	\N
62	Escritorio	DESK-INT-002	Cpu Desktop Intel I5 3	Intel	1120.00	10	Cpu Desktop- Infinytek Angel Warrior- Rgb-RTX4060- Intel Core I5 13400 (13va) -Asus H610mf- Ram 16gb DDR5 -Ssd 1T M.2 - Fuente 550w	\N
63	Escritorio	DESK-INT-003	Cpu Desktop Intel I5 4	\N	380.00	12	Cpu Desktop - Case Quasad Combo C3156 - Intel Core i5 12400 (12va) -Asus H610M-K-D4  - Ram 8GB DDR4  Ssd 500gb M.2 - Fuente 850w	\N
64	Escritorio	DESK-INT-009	Cpu Desktop Intel I7	Intel	1350.00	5	Cpu Desktop - Corsair 4000D - Intel Core i7 14700K (14va) - ASUS Prime Z790-P - Ram 32GB DDR5 - Ssd 1TB M.2 - Fuente 750w	\N
65	Escritorio	DESK-INT-011	Cpu Desktop Intel I9	Intel	3850.00	3	Cpu Desktop - Lian Li PC-O11 Dynamic - Intel Core i9 14900K (14va) - MSI MPG Z790 Carbon - Ram 64GB DDR5 - NVIDIA RTX 4090 24GB - Ssd 2TB M.2 - Fuente 1000w	\N
66	Escritorio	DESK-INT-004	Cpu Desktop Ryzen 5 1	Ryzen	495.00	7	Cpu Desktop -  CASE ANTEC AX20 ELITE -Ryzen 5 5600GT - GIGABYTE A520MK - Ram 16GB DDR4 - SSD 500gb M.2 - Fuente 650w	\N
67	Escritorio	DESK-RYZ-008	Cpu Desktop Ryzen 5 2	Ryzen	750.00	8	Cpu Desktop - Antec AX24 - Ryzen 5 7600 (7ma) - Gigabyte B650M - Ram 16GB DDR5 - NVIDIA RTX 3060 12GB - Ssd 512GB M.2 - Fuente 600w	\N
68	Escritorio	DESK-RYZ-014	Cpu Desktop Ryzen 5 3	Ryzen	680.00	10	Cpu Desktop - Cougar Archon 2 - Ryzen 5 5600 (5ta) - Gigabyte B450M DS3H - Ram 16GB DDR4 - AMD Radeon RX 6600 8GB - Ssd 512GB M.2 - Fuente 600w	\N
69	Escritorio	DESK-INT-005	Cpu Desktop Ryzen 7 1	Ryzen	1400.00	12	Cpu Desktop - Hp Victus 15 Tg02 0130 - Ryzen 7 5700g (5th) - AMD B550A - Ram 16gb Ssd 512gb *amd Rx 6600xt 8gb* - fuente 800w	\N
70	Escritorio	DESK-RYZ-010	Cpu Desktop Ryzen 7 2	Ryzen	580.00	12	Cpu Desktop - Quasad C3156 - Ryzen 7 5700G (5ta) - Asus Prime B550M-A - Ram 16GB DDR4 - Ssd 512GB M.2 - Fuente 550w	\N
71	Escritorio	DESK-RYZ-016	Cpu Desktop Ryzen 7 3	Ryzen	890.00	7	Cpu Desktop - Fantech Aero CG80 - Ryzen 7 8700G (8va) - Gigabyte A620M S2H - Ram 32GB DDR5 - Ssd 1TB M.2 - Fuente 650w	\N
72	Escritorio	DESK-RYZ-012	Cpu Desktop Ryzen 9	Ryzen	2400.00	4	Cpu Desktop - NZXT H5 Flow - Ryzen 9 7900X (7ma) - ASUS ROG Strix X670E-F - Ram 32GB DDR5 - NVIDIA RTX 4080 Super 16GB - Ssd 1TB M.2 - Fuente 850w	\N
215	Tablet	TAB-LEN-030	Lenovo Tab M9	Lenovo	175.00	44	Pantalla 9 pulg 3GB RAM 32GB WiFi Arctic Grey incluye Clear Case y Film-ZAC300	\N
233	Raton	MOU-APP-122	Mouse Apple Magic Multi-touch	APPLE	113.35	28	Mouse Apple Magic Multi-touch Wireless Surface Usb-c Black 	\N
250	Celular	CEL-HUA-032	Pura 70 Ultra	Huawei	1415.20	24	Huawei Pura 70 Ultra 16GB 512GB Cámara Retráctil Negro Ónix - HBP-LX9	\N
91	Almacenamiento	SSD-ADA-096	Disco Solido Adata	ADATA	60.15	52	Disco Solido 512gb Adata Asu650ss-512gt-r 2.5″	\N
289	Laptop	LAP-LEN-078	Lenovo Loq 15 RH543	Lenovo	875.65	11	Computadora del RON :P	{"procesador":"i513500H", "ram":"24GB", "disco":"1TB"}
\.


--
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: AdminTienda
--

COPY public.usuarios (id, nombre, email, password, rol, direccion) FROM stdin;
1	Administrador Maestro	admin@tienda.com	admin123	admin	Oficina Central
2	Ronald Ariel Pilataxi Sangacha	ronaldpilataxi@gmail.com	pancito123	cliente	La Quintana
3	User	u	u	cliente	u
4	Ariel Sangacha	arielsangacha@gmail.com	arlron	cliente	Mi casita
\.


--
-- Data for Name: valoraciones; Type: TABLE DATA; Schema: public; Owner: AdminTienda
--

COPY public.valoraciones (id, usuario_id, producto_sku, puntuacion, comentario, fecha) FROM stdin;
1	2	LAP-DELL-018	5	10/10 primera compu que compro xdd	2026-01-24
2	3	MON-ASU-140	2	Opinion de prueba	2026-01-24
3	4	LAP-LEN-078	4	10/10 solo que se me daño la pantalla por aplastarla muy duro	2026-01-25
\.


--
-- Name: detalle_pedidos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: AdminTienda
--

SELECT pg_catalog.setval('public.detalle_pedidos_id_seq', 11, true);


--
-- Name: pedidos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: AdminTienda
--

SELECT pg_catalog.setval('public.pedidos_id_seq', 5, true);


--
-- Name: productostienda_raw_id_seq; Type: SEQUENCE SET; Schema: public; Owner: AdminTienda
--

SELECT pg_catalog.setval('public.productostienda_raw_id_seq', 290, true);


--
-- Name: usuarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: AdminTienda
--

SELECT pg_catalog.setval('public.usuarios_id_seq', 4, true);


--
-- Name: valoraciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: AdminTienda
--

SELECT pg_catalog.setval('public.valoraciones_id_seq', 3, true);


--
-- Name: detalle_pedidos detalle_pedidos_pkey; Type: CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.detalle_pedidos
    ADD CONSTRAINT detalle_pedidos_pkey PRIMARY KEY (id);


--
-- Name: pedidos pedidos_pkey; Type: CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_pkey PRIMARY KEY (id);


--
-- Name: productostienda_raw productos_sku_unique; Type: CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.productostienda_raw
    ADD CONSTRAINT productos_sku_unique UNIQUE (sku);


--
-- Name: productostienda_raw productostienda_raw_pkey; Type: CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.productostienda_raw
    ADD CONSTRAINT productostienda_raw_pkey PRIMARY KEY (id);


--
-- Name: usuarios usuarios_email_key; Type: CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_email_key UNIQUE (email);


--
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- Name: valoraciones valoraciones_pkey; Type: CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.valoraciones
    ADD CONSTRAINT valoraciones_pkey PRIMARY KEY (id);


--
-- Name: detalle_pedidos detalle_pedidos_pedido_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.detalle_pedidos
    ADD CONSTRAINT detalle_pedidos_pedido_id_fkey FOREIGN KEY (pedido_id) REFERENCES public.pedidos(id);


--
-- Name: detalle_pedidos detalle_pedidos_producto_sku_fkey; Type: FK CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.detalle_pedidos
    ADD CONSTRAINT detalle_pedidos_producto_sku_fkey FOREIGN KEY (producto_sku) REFERENCES public.productostienda_raw(sku);


--
-- Name: pedidos pedidos_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id);


--
-- Name: valoraciones valoraciones_producto_sku_fkey; Type: FK CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.valoraciones
    ADD CONSTRAINT valoraciones_producto_sku_fkey FOREIGN KEY (producto_sku) REFERENCES public.productostienda_raw(sku);


--
-- Name: valoraciones valoraciones_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: AdminTienda
--

ALTER TABLE ONLY public.valoraciones
    ADD CONSTRAINT valoraciones_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id);


--
-- PostgreSQL database dump complete
--

\unrestrict TRzqssSwWznAb9IaPqZZ8tgZ5wLkC1DoPjd4V7oHAQLDQCSJ1guAm3xToNOga7v

