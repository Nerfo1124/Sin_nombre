package co.com.udistrital.sin_nombre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import co.com.udistrital.sin_nombre.dao.FormulaDAO;
import co.com.udistrital.sin_nombre.dao.RestablecerDAO;
import co.com.udistrital.sin_nombre.dao.SesionDAO;
import co.com.udistrital.sin_nombre.dao.SistemaDAO;
import co.com.udistrital.sin_nombre.dao.UsuarioDAO;
import co.com.udistrital.sin_nombre.vo.FormulaVO;
import co.com.udistrital.sin_nombre.vo.ReestablecerVO;
import co.com.udistrital.sin_nombre.vo.SesionVO;
import co.com.udistrital.sin_nombre.vo.SistemaVO;
import co.com.udistrital.sin_nombre.vo.UsuarioVO;

public class Pruebas extends AppCompatActivity {

    private FormulaVO formulaVO;
    private ReestablecerVO restablecerVO;
    private SesionVO sesionVO;
    private SistemaVO sistemaVO;
    private UsuarioVO usuarioVO;

    private FormulaDAO formula;
    private RestablecerDAO restablecer;
    private SesionDAO sesion;
    private SistemaDAO sistema;
    private UsuarioDAO usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        formula = new FormulaDAO(this);
        restablecer = new RestablecerDAO(this);
        sesion = new SesionDAO(this);
        sistema = new SistemaDAO(this);
        usuario = new UsuarioDAO(this);

        formulaVO = new FormulaVO();
        restablecerVO = new ReestablecerVO();
        sesionVO = new SesionVO();
        sistemaVO = new SistemaVO();
        usuarioVO = new UsuarioVO();

        restablecerVO.setPregunta1("¿Cuanto calzas?");
        restablecerVO.setPregunta2("¿?Dibujo animado favorito");
        restablecerVO.setRespuesta1("38");
        restablecerVO.setRespuesta2("Naruto");
        restablecerVO.setTamanoFuente("40");
        sesionVO.setUsuario("Nerfo");
        sesionVO.setContrasena("fernando1124");
        sistemaVO.setFrecuencia(1);
        sistemaVO.setTamanoFuente(40);
        formulaVO.setaVisualOD((float) 2.0);
        formulaVO.setaVisualOI((float) 2.0);

        usuarioVO.setNombreUsuario("Fernando");
        usuarioVO.setApellido1Usuario("Hernandez");
        usuarioVO.setApellido2Usuario("Anzola");
        usuarioVO.setSexo("Masculino");
        usuarioVO.setFechaNacimiento("24-11-1992");
        usuarioVO.setSesionUsuario(sesionVO);
        usuarioVO.setConfigUsuario(sistemaVO);
        usuarioVO.setRestablecerUsuario(restablecerVO);
        usuarioVO.setFormulaUsuario(formulaVO);

        usuario.insert(usuarioVO);

        /*usuarioVO = usuario.consult(1);
        Log.d("[Sin_nombre]", "Id: " + usuarioVO.getIdUsuario());
        Log.d("[Sin_nombre]", "Nombre: " + usuarioVO.getNombreUsuario());
        Log.d("[Sin_nombre]", "Apellido1: " + usuarioVO.getApellido1Usuario());
        Log.d("[Sin_nombre]", "Apellido2: " + usuarioVO.getApellido2Usuario());
        Log.d("[Sin_nombre]", "Fecha Nacimiento: " + usuarioVO.getFechaNacimiento());
        Log.d("[Sin_nombre]", "Sexo: " + usuarioVO.getSexo());

        List<UsuarioVO> usuarios = usuario.list();
        for(int i=0; i<usuarios.size();i++){
            usuarioVO = usuarios.get(i);
            Log.d("[Sin_nombre]", "Id: " + usuarioVO.getIdUsuario());
            Log.d("[Sin_nombre]", "Nombre: " + usuarioVO.getNombreUsuario());
            Log.d("[Sin_nombre]", "Apellido1: " + usuarioVO.getApellido1Usuario());
            Log.d("[Sin_nombre]", "Apellido2: " + usuarioVO.getApellido2Usuario());
            Log.d("[Sin_nombre]", "Fecha Nacimiento: " + usuarioVO.getFechaNacimiento());
            Log.d("[Sin_nombre]", "Sexo: " + usuarioVO.getSexo());
        }

        int ultimo = formula.consultLastID();
        Log.d("[Sin_nombre]", " Ultimo registro en Formula: " + ultimo);*/

        usuarioVO = usuario.consult(1);
        Log.d("[Sin_nombre]", "Id: " + usuarioVO.getIdUsuario());
        Log.d("[Sin_nombre]", "Nombre: " + usuarioVO.getNombreUsuario());
        Log.d("[Sin_nombre]", "Apellido1: " + usuarioVO.getApellido1Usuario());
        Log.d("[Sin_nombre]", "Apellido2: " + usuarioVO.getApellido2Usuario());
        Log.d("[Sin_nombre]", "Fecha Nacimiento: " + usuarioVO.getFechaNacimiento());
        Log.d("[Sin_nombre]", "Sexo: " + usuarioVO.getSexo());

        Log.d("[Sin_nombre]", "Id Sesion: " + usuarioVO.getSesionUsuario().getIdSesion());
        Log.d("[Sin_nombre]", "Id Formula: " + usuarioVO.getFormulaUsuario().getIdFormula());
        Log.d("[Sin_nombre]", "Id Sistema: " + usuarioVO.getConfigUsuario().getIdSistema());
        Log.d("[Sin_nombre]", "Id Restablecer: " + usuarioVO.getRestablecerUsuario().getIdReestablecer());
    }
}
