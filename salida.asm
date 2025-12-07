.data
output_0: .asciiz "ousheeet"
prueba_stringcito_0: .asciiz "puto"
prueba_stringcito_1: .asciiz "hola"

.text
.globl main
miFunc1:
sub $sp, $sp, 4
sw $ra, 0($sp)
lw $t0, 8($sp)
lw $t1, 4($sp)
mul $t2, $t0, $t1
lw $t3, 12($sp)
add $t4, $t3, $t2
move $v0, $t4
lw $ra, 0($sp)
addi $sp, $sp, 4
jr $ra
li $v0, 0
lw $ra, 0($sp)
addi $sp, $sp, 4
jr $ra
prueba:
sub $sp, $sp, 24
sw $ra, 0($sp)
li $t0, 2
sw $t0, 8($sp) #pp
li $t0, 1
sw $t0, 12($sp) #prueba2
li $t0, 0
sw $t0, 16($sp) #boolcito3
li $v0, 4
la $a0, output_0
syscall
li $v0, 4
la $a0, prueba_stringcito_1
syscall
if_1_encabezadoD1:
lw $t0, 16($sp)
lw $t1, 12($sp)
or $t2, $t0, $t1
bne $t2, $zero, if_bloque1D1
j if_2_encabezadoD1
if_bloque1D1:
li $t3, 1
sw $t3, 20($sp) #yy
j if_1_finD1
if_2_encabezadoD1:
li $t0, 3
sw $t0, 24($sp) #zzz
if_1_finD1:
lw $t0, 8($sp)
move $v0, $t0
lw $ra, 0($sp)
addi $sp, $sp, 24
jr $ra
li $v0, 0
lw $ra, 0($sp)
addi $sp, $sp, 24
jr $ra
main:
sub $sp, $sp, 64
li $t0, 1
sw $t0, 0($sp) #a
lw $t0, 0($sp)
sub $sp, $sp , 4 #Parametro
sw $t0, 0($sp) #Carga en la pila del parámetro
li $t1, 4
sub $sp, $sp , 4 #Parametro
sw $t1, 0($sp) #Carga en la pila del parámetro
li $t2, 5
sub $sp, $sp , 4 #Parametro
sw $t2, 0($sp) #Carga en la pila del parámetro
jal miFunc1
move $v0, $t3
addi $sp, $sp, 12
sw $t3, 16($sp) #c
li $t0, 1
sw $t0, 0($sp) #a
li $t0, 5
lw $t1, 0($sp)
mul $t2, $t0, $t1
lw $t3, 4($sp)
li $t4, 2
mul $t5, $t3, $t4
add $t6, $t2, $t5
li $t7, 5
add $t8, $t6, $t7
li $t9, 10
li $s0, 5
mul $s1, $t9, $s0
li $s2, 2
div $s3, $s1, $s2
add $s4, $t8, $s3
lw $s5, 0($sp)
add $s6, $s4, $s5
sw $s6, 0($sp) #a
li $t0, 5
li $t1, 10
add $t2, $t0, $t1
lw $t3, 0($sp)
sub $sp, $sp , 4 #Parametro
sw $t3, 0($sp) #Carga en la pila del parámetro
lw $t4, 8($sp)
sub $sp, $sp , 4 #Parametro
sw $t4, 0($sp) #Carga en la pila del parámetro
li $t5, 5
sub $sp, $sp , 4 #Parametro
sw $t5, 0($sp) #Carga en la pila del parámetro
jal miFunc1
move $v0, $t6
addi $sp, $sp, 12
li $t7, 15
mul $t8, $t7, $t6
add $t9, $t2, $t8
sw $t9, 4($sp) #b
li $t0, 0
sw $t0, 20($sp) #boolcito2
li $t0, 1
sw $t0, 24($sp) #prueba
if_2_encabezadoD2:
lw $t0, 20($sp)
lw $t1, 24($sp)

bne $t2, $zero, if_bloque2D2
j if_3_encabezadoD2
if_bloque2D2:
li $t3, 1
sw $t3, 28($sp) #y
j if_1_finD2
if_3_encabezadoD2:
lw $t0, 4($sp)
lw $t1, 0($sp)
sgt $t2, $t0, $t1
bne $t2, $zero, if_bloque3D2
j if_5_encabezadoD2
if_bloque3D2:
li $t3, 2
sw $t3, 32($sp) #x
j if_1_finD2
if_5_encabezadoD2:
li $t0, 3
sw $t0, 36($sp) #z
j if_6_encabezadoD2
if_6_encabezadoD2:
j if_1_finD2
if_1_finD2:
jal prueba
move $v0, $t0
addi $sp, $sp, 0
sw $t0, 40($sp) #asrasdas
li $t0, 0
sw $t0, 44($sp) #contador
loop_inicio_1:
lw $t0, 44($sp)
li $t1, 1
add $t2, $t0, $t1
sw $t2, 44($sp) #contador
if_5_encabezadoD3:
lw $t0, 44($sp)
li $t1, 1
seq $t2, $t0, $t1
bne $t2, $zero, if_bloque4D3
j if_6_encabezadoD3
if_bloque4D3:
li $t3, 4
sw $t3, 48($sp) #caso1
j if_1_finD3
if_6_encabezadoD3:
lw $t0, 44($sp)
li $t1, 3
seq $t2, $t0, $t1
bne $t2, $zero, if_bloque5D3
j if_1_finD3
if_bloque5D3:
li $t3, 3
sw $t3, 52($sp) #caso3
j if_1_finD3
if_7_encabezadoD3:
li $t0, 2
sw $t0, 56($sp) #otro
j if_8_encabezadoD3
if_8_encabezadoD3:
j if_1_finD3
if_1_finD3:
lw $t0, 44($sp)
li $t1, 5
sgt $t2, $t0, $t1
bne $t2, $zero, loop_fin_1
j loop_inicio_1
loop_fin_1:
li $t3, 0
sw $t3, 60($sp) #contador1
loop_inicio_2:
lw $t0, 60($sp)
li $t1, 1
add $t2, $t0, $t1
sw $t2, 60($sp) #contador1
lw $t0, 60($sp)
li $t1, 3
sgt $t2, $t0, $t1
bne $t2, $zero, loop_fin_2
j loop_inicio_2
loop_fin_2:
addi $sp, $sp, 64
li $v0, 10
syscall
