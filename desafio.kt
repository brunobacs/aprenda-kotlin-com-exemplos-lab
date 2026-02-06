import java.rmi.server.UID
import kotlin.math.max

// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)

enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

class Usuario (val nome: String, val sobrenome : String)

data class ConteudoEducacional(var nome: String, var duracao: Int, val nivel : Nivel)

data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>) {

    val nivel : Nivel
        get() = calcularNivelNinja()
    val inscritos = mutableListOf<Usuario>()
    fun matricular(usuario: Usuario) {
        inscritos.add(usuario)
    }

    fun calcularNivelNinja(): Nivel {
        return conteudos.groupBy { it.nivel }
            .maxByOrNull { it.value.size }?.key ?: Nivel.BASICO
    }

    fun definirNivel() : Nivel {
        var basico = 0
        var intermediario =0
        var dificil = 0

        for (conteudo in conteudos){
            when (conteudo.nivel) {
                Nivel.BASICO -> basico ++
                Nivel.INTERMEDIARIO -> intermediario ++
                Nivel.DIFICIL -> dificil ++
            }
        }

        return when {
            dificil >= intermediario && dificil >= basico -> Nivel.DIFICIL
            intermediario >= basico -> Nivel.INTERMEDIARIO
            else -> Nivel.BASICO
        }

    }
    
    fun getNivel () : Nivel{
        return nivel
    }

    fun getInscritos () : List<Usuario> {
        return inscritos
    }


}

fun main() {
    

    val usuarioA = Usuario("Joao", "A")
    val usuarioB = Usuario("Jose", "B")
    val usuarioC = Usuario("Pedro", "C")

    val conteudo1 = ConteudoEducacional("Intro a Java", 60, Nivel.BASICO)
    val conteudo2 = ConteudoEducacional("POO", 60, Nivel.INTERMEDIARIO)
    val conteudo3 = ConteudoEducacional("SOLID", 30, Nivel.INTERMEDIARIO)
    val conteudo4 = ConteudoEducacional("Engenharia de Software", 120, Nivel.DIFICIL)

    val conteudos = listOf(conteudo1, conteudo2, conteudo3, conteudo4)

    val formacao = Formacao("Curso de Java", conteudos)

    formacao.matricular(usuarioA)
    formacao.matricular(usuarioB)

    println("Formação: ${formacao.nome}")

    println("Nível calculado (Ninja): ${formacao.getNivel()}")
    println("Nível calculado (Manual): ${formacao.definirNivel()}")

    println("--- Inscritos ---")
    for (u in formacao.getInscritos()) {
        println("Usuário: ${u.nome} ${u.sobrenome}")
    }

}
