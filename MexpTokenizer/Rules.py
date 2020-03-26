tokens = [
    #'corta',
    #'larga',
    'alpha',
    'pi',
    'beta',
    'gamma',
    'lambda',
    'sigma',
    'mu',
    'delta',
    'phi',
    'theta',
    'Delta',

    'frac',
    'sqrt',
    'sin',
    'times',
    'cos',
    'int',
    'sum',
    'tan',
    'log',
    'rightarrow',
    'lim',
    'infty',
    'leq',
    'div',
    'pm',
    'geq',
    'neq',
    'gt',
    'in',
    'forall',
    'exists',


    #symbols
    'lparen',
    'rparen',
    'dot',
    'bar',# |
    'exclamation', #!
    'lsqubracket',#[
    'rsqubracket',#]
    'comma', #,

    #specials
    'lcurly',
    'rcurly',
    'ldots',
    'lt',
    'mathrm',



    #lower letters
    'x',
    'a',
    'n',
    'y',
    'b',
    'z',
    'd',
    'i',
    'c',
    'k',
    't',
    'e',
    'f',
    'p',
    'm',
    'r',
    'j',
    'g',
    'u',
    'v',
    'q',
    'h',
    's',
    'w',
    'l',
    'o',

    #CAPITAL
    'C',
    'B',
    'X',
    'A',
    'F',
    'R',
    'E',
    'L',
    'N',
    'S',
    'P',
    'V',
    'T',
    'M',
    'Y',
    'H',
    'G',
    'I',
    'D',

    #short signs
    'minus',#-
    'plus',#+
    'equal', #=
    'over', #/

    'zero',
    'one',
    'two',
    'three',
    'four',
    'five',
    'six',
    'seven',
    'eight',
    'nine',

    'subindex',
    'superindex',
    'cdots',
    'left',
    'right',
    'Bigg',
    'prime',
    
    'explicit_lcurly',
    'explicit_rcurly',
    'Big',
    'explicit_gt', #>
    'explicit_lt', #<   
]
#math symbols
def t_frac(t):
    r'\\frac'
    return t
def t_sqrt(t):
    r'\\sqrt'
    return t

def t_sin(t):
    r'\\sin'
    return t

def t_times(t):  
    r'\\times'
    return t
    
def t_cos(t):
    r'\\cos'
    return t

def t_int(t):
    r'\\int'
    return t

def t_sum(t):
    r'\\sum'
    return t

def t_tan(t):
    r'\\tan'
    return t

def t_log(t):
    r'\\log'
    return t

def t_rightarrow(t):
    r'\\rightarrow'
    return t

def t_lim(t):
    r'\\lim'
    return t
def t_infty(t):
    r'\\infty'
    return t

def t_leq(t):
    r'\\leq'
    return t

def t_div(t):
    r'\\div'
    return t

def t_pm(t):
    r'\\pm'
    return t

def t_geq(t):
    r'\\geq'
    return t

def t_neq(t):
    r'\\neq'
    return t

def t_gt(t):
    r'\\gt'
    return t

def t_in(t):
    r'\\in'
    return t

def t_forall(t):
    r'\\forall'
    return t

def t_exists(t):
    r'\\exists'
    return t
#Greek Letters
def t_alpha(t):
    r'\\alpha'
    return t

def t_lambda(t):
    r'\\lambda'
    return t

def t_gamma(t):
    r'\\gamma'
    return t

def t_beta(t):
    r'\\beta'
    return t

def t_sigma(t):
    r'\\sigma'
    return t

def t_phi(t):
    r'\\phi'
    return t

def t_mu(t):
    r'\\mu'
    return t

def t_pi(t):
    r'\\pi'
    return t

def t_delta(t):
    r'\\delta'#r'\\(d|D)elta'
    return t

def t_theta(t):
    r'\\theta'
    return t

def t_Delta(t):
    r'\\Delta'
    return t

def t_lparen(t):
    r'\('
    return t
def t_rparen(t):
    r'\)'
    return t
def t_dot(t):
    r'\.'
    return t
def t_bar(t):
    r'\|'
    return t

def t_exclamation(t):
    r'\!'
    return t
def t_lsqubracket(t):
    r'\['
    return t
def t_rsqubracket(t):
    r'\]'
    return t

def t_comma(t):
    r'\,'
    return t 

#especials
def t_lcurly(t):
    r'\{'
    return t

def t_rcurly(t):
    r'\}'
    return t


def t_x(t):
	'x'
	return t
def t_a(t):
	'a'
	return t
def t_n(t):
	'n'
	return t
def t_y(t):
	'y'
	return t
def t_b(t):
	'b'
	return t
def t_z(t):
	'z'
	return t
def t_d(t):
	'd'
	return t
def t_i(t):
	'i'
	return t
def t_c(t):
	'c'
	return t
def t_k(t):
	'k'
	return t
def t_t(t):
	't'
	return t
def t_e(t):
	'e'
	return t
def t_f(t):
	'f'
	return t
def t_p(t):
	'p'
	return t
def t_m(t):
	'm'
	return t
def t_r(t):
	'r'
	return t
def t_j(t):
	'j'
	return t
def t_g(t):
	'g'
	return t
def t_u(t):
	'u'
	return t
def t_v(t):
	'v'
	return t
def t_q(t):
	'q'
	return t
def t_h(t):
	'h'
	return t
def t_s(t):
	's'
	return t
def t_w(t):
	'w'
	return t
def t_l(t):
	'l'
	return t
def t_o(t):
	'o'
	return t
def t_C(t):
	'C'
	return t
def t_B(t):
	'B'
	return t
def t_X(t):
	'X'
	return t
def t_A(t):
	'A'
	return t
def t_F(t):
	'F'
	return t
def t_R(t):
	'R'
	return t
def t_E(t):
	'E'
	return t
def t_L(t):
	'L'
	return t
def t_N(t):
	'N'
	return t
def t_S(t):
	'S'
	return t
def t_P(t):
	'P'
	return t
def t_V(t):
	'V'
	return t
def t_T(t):
	'T'
	return t
def t_M(t):
	'M'
	return t
def t_Y(t):
	'Y'
	return t
def t_H(t):
	'H'
	return t
def t_G(t):
	'G'
	return t
def t_I(t):
	'I'
	return t

def t_D(t):
    'D'
    return t

def t_minus(t):
    r'\-'
    return t
    
def t_plus(t):
    r'\+'
    return t

def t_equal(t):
    r'\='
    return t
def t_over(t):
    r'\/'
    return t

def t_zero(t):
    r'0'
    return t

def t_one(t):
    r'1'
    return t    

def t_two(t):
    r'2'
    return t

def t_three(t):
    r'3'
    return t

def t_four(t):
    r'4'
    return t

def t_five(t):
    r'5'
    return t

def t_six(t):
    r'6'
    return t

def t_seven(t):
    r'7'
    return t

def t_eight(t):
    r'8'
    return t

def t_nine(t):
    r'9'
    return t

def t_subindex(t):
    r'\_'
    return t

def t_superindex(t):
    r'\^'
    return t

def t_cdots(t):
    r'\\cdots'
    return t

def t_left(t):
    r'\\left'
    return t

def t_right(t):
    r'\\right'
    return t
def t_Bigg(t):
    r'\\Bigg'
    return t

def t_ldots(t):
    r'\\ldots'
    return t

def t_lt(t):
    r'\\lt'
    return t

def t_mathrm(t):
    r'\\mathrm'
    return t

def t_prime(t):
    r'\\prime'
    return t

def t_explicit_lcurly(t):
    r'\\{'
    return t

def t_explicit_rcurly(t):
    r'\\}'
    return t
def t_Big(t):
    r'\\Big'
    return t

def t_explicit_gt(t):
    r'>'
    return t

def t_explicit_lt(t):
    r'<'
    return t
        
"""    
def t_corta(t):
    'l'
    return t
def t_larga(t):
    r'll'
    return t
def t_CONSTANTE_FLOTANTE(t):
    r'(-|\u002B)?\d*\.\d+'
    return t
"""
"""
def t_URL(t):
    r'^(http)://.*/(inicio)$'
    return t

def t_COMENTARIO(t):
    r'//.*|/\*(.|\n)*\*/'
    return t

def t_RESTA(t):
    r'-'
    return t

def t_GOOD(t):
    r'(go{1,2}d)'
    return t

def t_IDENTIFICADOR(t):
    r'[a-zA-Z_]\w*'
    return t
"""
t_ignore = ' \t$\'\"\\mbox\\vtop\\hbox'


def t_error(t):
    print ("Caracter ilegal '%s'" % t.value[0])
    print("-line "+str(t.lineno))
    t.lexer.skip(1)
    exit(-1)

def t_NEWLINE(t):
    r'\n+'
    t.lexer.lineno += len(t.value)