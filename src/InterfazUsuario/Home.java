 package InterfazUsuario;

import ComponentesBeauty.*;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Juan José Estrada Valtierra on 22/04/17.
 */
public class Home extends JFrame {
    private BeautyCircleButton imgUsuario;
    private JLabel lblNombreUsuario;
    private BeautyImageButton btnMenu, btnMinimizar, btnCerrar;
    private JPanel panelInfoUsuario, panelMenu;
    private JPanel panelHome, panelUsuario, panelProducto,panelPaciente,panelVenta,panelBaseDatos;
    private JLabel lblFondo;
    boolean paseMenuInfo = false;
    boolean paseMenuMenu = false;
    boolean paseMenuRol = false;
    boolean paseMenuTipo = false;

    public Home() {
        this.setLayout(null);
        this.setTitle("Clinical Control");
        this.setIconImage(new ImageIcon("src/Iconos/icn_Logo.png").getImage());
        this.setUndecorated(true);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        Shape forma = new RoundRectangle2D.Double(0,0,this.getWidth(),this.getHeight(),27,27);
        AWTUtilities.setWindowShape(this,forma);
        componentesBase();
        add(getPanelInfoUsuario());
        add(getPanelMenu());
        add(getPanelHome());
        add(getPanelUsuario());
        add(getPanelProducto());
        add(getPanelPaciente());
        add(getPanelVenta());
        add(getPanelBaseDatos());
    }

    private void componentesBase() {
        imgUsuario = new BeautyCircleButton(establecerIcono("FotoPerfil",40,40),0,5,40,40);
        imgUsuario.setBackground(getBackground());

        lblNombreUsuario = new JLabel("Juan José Estrada Valtierra");
        lblNombreUsuario.setBounds(45,10,250,30);
        lblNombreUsuario.setForeground(Color.WHITE);
        lblNombreUsuario.setFont(appleFont());
        lblNombreUsuario.setHorizontalAlignment(JLabel.CENTER);

        btnMenu = new BeautyImageButton(establecerIcono("Menu",20,20),320,15,20,20);
        btnMinimizar = new BeautyImageButton(establecerIcono("Minimizar",10,1),745,18,10,10);
        btnCerrar = new BeautyImageButton(establecerIcono("Cerrar",10,10),775,12,10,10);

        imgUsuario.addActionListener((ActionEvent) -> {
            if(paseMenuInfo) {
                panelInfoUsuario.setVisible(true);
                paseMenuInfo = false;
            } else {
                panelInfoUsuario.setVisible(false);
                paseMenuInfo = true;
            }
        });

        btnMenu.addActionListener((ActionEvent) -> {
            if(paseMenuMenu) {
                panelMenu.setVisible(true);
                paseMenuMenu = false;
            } else {
                panelMenu.setVisible(false);
                paseMenuMenu = true;
            }
        });

        btnMinimizar.addActionListener((ActionEvent) -> {
            this.setExtendedState(1);
        });

        btnCerrar.addActionListener((ActionEvent) -> {
            System.exit(0);
        });


        this.add(imgUsuario);
        this.add(lblNombreUsuario);
        this.add(btnMenu);
        this.add(btnMinimizar);
        this.add(btnCerrar);
    }


    /**
     *
     * @return Vista panel Home
     */

    private JPanel getPanelHome() {
        panelHome = new JPanel();
        panelHome.setLayout(null);
        panelHome.setSize(800,500);
        panelHome.setOpaque(true);

        lblFondo = new JLabel();
        lblFondo.setSize(getWidth(), getHeight());
        lblFondo.setIcon(establecerImagen("fondoTitulo", lblFondo.getWidth(), lblFondo.getHeight()));
        panelHome.add(lblFondo);
        return panelHome;
    }

    private JPanel getPanelUsuario() {
        panelUsuario = new JPanel();
        panelUsuario.setLayout(null);
        panelUsuario.setBounds(0,0,800,500);


        JLabel lblFondo = new JLabel();
        lblFondo.setIcon(establecerImagen("fondoGeneral",panelUsuario.getWidth(),panelUsuario.getHeight()));
        lblFondo.setBounds(0,0,panelUsuario.getWidth(),panelUsuario.getHeight());

        BeautyCircleButton btnImagenPerfil = new BeautyCircleButton(establecerIcono("FotoPerfil",150,150),40,70,150,150);

        BeautyTextField txtBuscar = new BeautyTextField("Buscar...",300,120,300,30);
        txtBuscar.setHint("Buscar...");

        BeautyImageButton btnNuevo = new BeautyImageButton(establecerIcono("Editar",20,20),620,130,20,20);
        BeautyImageButton btnEditar = new BeautyImageButton(establecerIcono("Editar",20,20),660,130,20,20);

        BeautyComboBox comboBoxRol = new BeautyComboBox("Rol",40,250,120,20);
        BeautyButton btnMedico = new BeautyButton("Medico",0,0,100,20);
        BeautyButton btnAdministrativo = new BeautyButton("Administrativo",0,0,100,20);
        BeautyButton btnNormal = new BeautyButton("Normal",0,0,100,20);
        JPanel panelRol = new JPanel(new GridLayout(3,1));
        panelRol.setBackground(new Color(0,0,0,198));
        panelRol.setBounds(40,280,100,100);
        panelRol.add(btnMedico);
        panelRol.add(btnAdministrativo);
        panelRol.add(btnNormal);
        panelRol.setVisible(false);
        this.add(panelRol);

        comboBoxRol.addActionListener((ActionEvent) -> {
            if(paseMenuRol) {
                panelRol.setVisible(true);
                paseMenuRol = false;
            } else {
                panelRol.setVisible(false);
                paseMenuRol = true;
            }
        });

        btnMedico.addActionListener((ActionEvent) -> {
            comboBoxRol.setText("Medico");
            panelRol.setVisible(false);
        });

        btnAdministrativo.addActionListener((ActionEvent) -> {
            comboBoxRol.setText("Administrativo");
            panelRol.setVisible(false);
        });

        btnNormal.addActionListener((ActionEvent) -> {
            comboBoxRol.setText("Normal");
            panelRol.setVisible(false);
        });


        BeautyTextField txtNombre = new BeautyTextField("Nombre",60,290,200,20);
        txtNombre.setHint("Nombre");

        BeautyTextField txtApellidoPaterno = new BeautyTextField("Apellido Paterno",310,290,200,20);
        txtApellidoPaterno.setHint("Apellido Paterno");

        BeautyTextField txtApellidoMaterno = new BeautyTextField("Apellido Materno",570,290,200,20);
        txtApellidoMaterno.setHint("Apellido Materno");

        BeautyTextField txtCorreo = new BeautyTextField("Correo",60, 390,200,20);
        txtCorreo.setHint("Correo");

        BeautyPasswordField txtPass = new BeautyPasswordField("Contraseña",310,390,200,20);
        txtPass.setHint("Contraseña");

        BeautyPasswordField txtPass2 = new BeautyPasswordField("Repetir contraseña",2570,390,200,20);
        txtPass2.setHint("Repetir contraseña");


        BeautyBlackButton btnGuardar = new BeautyBlackButton("Guardar",630,440,100,30);

        BeautyCheckbox checkActivo = new BeautyCheckbox("Activo",60,450,200);
        BeautyCheckbox checkAdministrador = new BeautyCheckbox("Administrador" , 300,450,150);

        panelUsuario.add(btnImagenPerfil);
        panelUsuario.add(txtBuscar);
        panelUsuario.add(btnNuevo);
        panelUsuario.add(btnEditar);
        panelUsuario.add(comboBoxRol);
        panelUsuario.add(txtNombre);
        panelUsuario.add(txtApellidoPaterno);
        panelUsuario.add(txtApellidoMaterno);
        panelUsuario.add(txtCorreo);
        panelUsuario.add(txtPass);
        panelUsuario.add(txtPass2);
        panelUsuario.add(checkActivo);
        panelUsuario.add(checkAdministrador);
        panelUsuario.add(btnGuardar);
        panelUsuario.add(lblFondo);

        panelUsuario.setVisible(false);
        return panelUsuario;
    }

    private JPanel getPanelProducto() {
        panelProducto = new JPanel();
        panelProducto.setLayout(null);
        panelProducto.setBounds(0,0,800,500);

        return panelProducto;
    }

    private JPanel getPanelPaciente() {
        panelPaciente = new JPanel();
        panelPaciente.setBounds(0,0,800,500);
        panelPaciente.setBackground(new Color(199, 164, 113,198));
        panelPaciente.setVisible(true);
        return panelPaciente;
    }

    private JPanel getPanelVenta() {
        panelVenta = new JPanel();
        panelVenta.setBounds(0,0,800,500);
        panelVenta.setBackground(new Color(0, 104, 73,198));
        panelVenta.setVisible(true);
        return panelVenta;
    }

    private JPanel getPanelBaseDatos() {
        panelBaseDatos = new JPanel();
        panelBaseDatos.setBounds(0,0,800,500);
        panelBaseDatos.setBackground(new Color(118, 117, 1,198));
        panelBaseDatos.setVisible(true);
        return panelBaseDatos;
    }

    /**
     *
     * @return Menu de la información del usuario
     */

    private JPanel getPanelInfoUsuario() {
        panelInfoUsuario = new JPanel(new GridLayout(3,1));
        JLabel lblRol = new JLabel("Rol : ");
        JLabel lblTipo = new JLabel("Tipo: ");
        JLabel lblCorreo = new JLabel("Correo: ");
        lblRol.setFont(appleFont());
        lblTipo.setFont(appleFont());
        lblCorreo.setFont(appleFont());
        lblRol.setForeground(Color.WHITE);
        lblTipo.setForeground(Color.WHITE);
        lblCorreo.setForeground(Color.WHITE);

        panelInfoUsuario.setBounds(10,45,200,100);

        panelInfoUsuario.add(lblRol);
        panelInfoUsuario.add(lblTipo);
        panelInfoUsuario.add(lblCorreo);

        panelInfoUsuario.setBackground(new Color(0, 0, 0, 198));
        panelInfoUsuario.setVisible(false);

        return panelInfoUsuario;
    }


    /**
     *
     * @return Menu General
     */

    private JPanel getPanelMenu() {
        panelMenu = new JPanel(new GridLayout(7,1));
        panelMenu.setBackground(new Color(0,0,0,198));
        panelMenu.setBounds(320,38,200,200);

        BeautyButton btnHome = new BeautyButton("Home",  0, 0, panelMenu.getWidth(), 30);
        BeautyButton btnUsuario = new BeautyButton("Usuario",  0, 0, panelMenu.getWidth(), 30);
        BeautyButton btnProducto = new BeautyButton("Producto",  0, 0, panelMenu.getWidth(), 30);
        BeautyButton btnPaciente = new BeautyButton("Paciente", 0, 0, panelMenu.getWidth(), 30);
        BeautyButton btnVenta = new BeautyButton("Venta", 0, 0, panelMenu.getWidth(), 30);
        BeautyButton btnBaseDatos = new BeautyButton("Base de Datos",0, 0, panelMenu.getWidth(), 30);
        BeautyButton btnSalir = new BeautyButton("Salir", 0, 0, panelMenu.getWidth(), 30);

        btnHome.setHorizontalAlignment(JButton.LEFT);
        btnUsuario.setHorizontalAlignment(JButton.LEFT);
        btnProducto.setHorizontalAlignment(JButton.LEFT);
        btnPaciente.setHorizontalAlignment(JButton.LEFT);
        btnVenta.setHorizontalAlignment(JButton.LEFT);
        btnBaseDatos.setHorizontalAlignment(JButton.LEFT);
        btnSalir.setHorizontalAlignment(JButton.LEFT);

        btnHome.addActionListener((ActionEvent) -> {
            panelInfoUsuario.setVisible(false);
            panelMenu.setVisible(false);
            panelProducto.setVisible(false);
            panelPaciente.setVisible(false);
            panelBaseDatos.setVisible(false);
            panelUsuario.setVisible(false);
            panelHome.setVisible(true);
            panelVenta.setVisible(false);
        });

        btnUsuario.addActionListener((ActionEvent) -> {
            panelInfoUsuario.setVisible(false);
            panelMenu.setVisible(false);
            panelHome.setVisible(false);
            panelProducto.setVisible(false);
            panelPaciente.setVisible(false);
            panelBaseDatos.setVisible(false);
            panelUsuario.setVisible(true);
            panelVenta.setVisible(false);
        });

        btnProducto.addActionListener((ActionEvent) -> {
            panelInfoUsuario.setVisible(false);
            panelMenu.setVisible(false);
            panelHome.setVisible(false);
            panelProducto.setVisible(true);
            panelPaciente.setVisible(false);
            panelBaseDatos.setVisible(false);
            panelUsuario.setVisible(false);
            panelVenta.setVisible(false);
        });

        btnPaciente.addActionListener((ActionEvent) -> {
            panelInfoUsuario.setVisible(false);
            panelMenu.setVisible(false);
            panelHome.setVisible(false);
            panelProducto.setVisible(false);
            panelPaciente.setVisible(true);
            panelBaseDatos.setVisible(false);
            panelUsuario.setVisible(false);
            panelVenta.setVisible(false);
        });

        btnVenta.addActionListener((ActionEvent) -> {
            panelInfoUsuario.setVisible(false);
            panelMenu.setVisible(false);
            panelHome.setVisible(false);
            panelProducto.setVisible(false);
            panelPaciente.setVisible(false);
            panelBaseDatos.setVisible(false);
            panelUsuario.setVisible(false);
            panelVenta.setVisible(true);
        });

        btnBaseDatos.addActionListener((ActionEvent) -> {
            panelInfoUsuario.setVisible(false);
            panelMenu.setVisible(false);
            panelHome.setVisible(false);
            panelProducto.setVisible(false);
            panelPaciente.setVisible(false);
            panelBaseDatos.setVisible(true);
            panelUsuario.setVisible(false);
            panelVenta.setVisible(false);
        });

        btnSalir.addActionListener((ActionEvent) -> {System.exit(0);});

        panelMenu.add(btnHome);
        panelMenu.add(btnUsuario);
        panelMenu.add(btnProducto);
        panelMenu.add(btnPaciente);
        panelMenu.add(btnVenta);
        panelMenu.add(btnBaseDatos);
        panelMenu.add(btnSalir);

        panelMenu.setVisible(false);

        return panelMenu;
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * @param archivo
     * @param ancho
     * @param alto
     * @return icono
     */

    public static Icon establecerIcono(String archivo,int ancho, int alto) {
        ImageIcon imagen = new ImageIcon("src/Iconos/icn_" + archivo + ".png");
        Icon i = new ImageIcon(imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
        return i;
    }

    /**
     *
     * @param archivo
     * @param ancho
     * @param alto
     * @return Imagen
     */

    public static Icon establecerImagen(String archivo,int ancho, int alto) {
        ImageIcon imagen = new ImageIcon("src/Imagenes/" + archivo + ".png");
        Icon i = new ImageIcon(imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
        return i;
    }

    /**
     *
     * @return Fuente de Apple
     */

    public static Font appleFont() {
        try {
            Font appleFont = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream(new File("src/Fuentes/MYRIADAT.TTF"))).
                    deriveFont(Font.PLAIN, 14);
            return appleFont;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Home c= new Home();
        c.repaint();
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
