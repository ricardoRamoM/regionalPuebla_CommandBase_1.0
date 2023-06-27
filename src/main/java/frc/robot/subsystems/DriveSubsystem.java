package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    //Functions to easily use motors and sensors

    
    static byte idMotorIzqAdel = 3;
    static byte idMotorIzqAtras = 4;

    static byte idMotorDerAdel = 1;
    static byte idMotorDerAtras = 2;
    
    static CANSparkMax motorIzqAdelante = new CANSparkMax(idMotorIzqAdel, MotorType.kBrushless);
    static CANSparkMax motorIzqAtras = new CANSparkMax(idMotorIzqAtras, MotorType.kBrushless);
    static CANSparkMax motorDerAdelante = new CANSparkMax(idMotorDerAdel, MotorType.kBrushless);
    static CANSparkMax motorDerAtras = new CANSparkMax(idMotorDerAtras, MotorType.kBrushless);

    SparkMaxPIDController pidDer = motorDerAdelante.getPIDController();
    SparkMaxPIDController pidIzq = motorIzqAdelante.getPIDController();

    MotorControllerGroup Izq = new MotorControllerGroup(motorIzqAdelante, motorIzqAtras);

    RelativeEncoder encoderIzqAdel;
    RelativeEncoder encoderIzqAtras;
    RelativeEncoder encoderDerAdel;
    RelativeEncoder encoderDerAtras;
    
    static MotorControllerGroup motoresIzq  = new MotorControllerGroup(motorIzqAdelante, motorIzqAtras);
    static MotorControllerGroup motoresDer = new MotorControllerGroup(motorDerAdelante, motorDerAtras);

    static DifferentialDrive chasis = new DifferentialDrive(motoresIzq, motoresDer);

    double valorEncoderDerAdel;
    double valorEncoderIzqAdel;

    Solenoid solenoidVelocidades = new Solenoid(PneumaticsModuleType.REVPH, 1);  

    double relacionBaja = 1.4; 
    double relacionAlta = 0.9;
    double relacionUsada =  relacionBaja;

    double diametroLlanta = 0.1524; // Metros
    double distanciaRecorrida;

    AHRS NavX = new AHRS(Port.kMXP);
  
    boolean tankDrive = true;
    boolean arcadeDrive = !tankDrive;
    boolean tipoVelocidad;
    boolean velocidadBaja = false;
    boolean velocidadAlta = !velocidadBaja;

    //Reset Encoder Values
    public void resetEncodersChasis(){
        encoderIzqAdel.setPosition(0);
        encoderIzqAtras.setPosition(0);
        encoderDerAdel.setPosition(0);
        encoderDerAtras.setPosition(0);
    }

    //Sets gyroscope yaw to 0
    public void resetGyro(){
        NavX.zeroYaw();
    }

    /*
    * Returns gyroscope Yaw Heading 
    * @return double angle heading
    */
    public double getGyro(){
        return NavX.getAngle();
    }

  
    public DriveSubsystem () {
        motorDerAdelante.restoreFactoryDefaults();
        motorDerAtras.restoreFactoryDefaults();
        motorIzqAdelante.restoreFactoryDefaults();
        motorIzqAtras.restoreFactoryDefaults();

        //SetUp Encoders
        encoderIzqAdel = motorIzqAdelante.getEncoder();
        encoderIzqAtras = motorIzqAtras.getEncoder();
        encoderDerAdel = motorDerAdelante.getEncoder();
        encoderDerAtras = motorDerAtras.getEncoder();

        //SetUp spark Max Chasis
        //roult tiene chasis en coast
        motorDerAdelante.setIdleMode(IdleMode.kCoast);
        motorDerAdelante.setIdleMode(IdleMode.kCoast);
        motorDerAdelante.setIdleMode(IdleMode.kCoast);
        motorDerAdelante.setIdleMode(IdleMode.kCoast);

        //SetUp inverted Motors
        motoresDer.setInverted(false);
        motoresIzq.setInverted(!motoresDer.getInverted());

        //Sensor Reset 
        resetEncodersChasis();                
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        valorEncoderIzqAdel = encoderIzqAdel.getPosition();
        valorEncoderDerAdel = -encoderDerAdel.getPosition();

        SmartDashboard.putNumber("encoderIzqAdel ", valorEncoderIzqAdel);
        SmartDashboard.putNumber("encoderDerAdel ", valorEncoderDerAdel);

        distanciaRecorrida = valorEncoderIzqAdel * (Math.PI * diametroLlanta / relacionUsada)/10;
        SmartDashboard.putNumber("distancia", distanciaRecorrida);     


        /*SmartDashboard.putBoolean("Baja", velocidadBaja);
        SmartDashboard.putBoolean("Alta", velocidadAlta);  

        SmartDashboard.putBoolean("Tanque", tankDrive);
        SmartDashboard.putBoolean("Arcade", arcadeDrive);*/
    }

    public static void setMotoresVelocidadTanque(double velocidadIzq, double velocidadDer){
        chasis.tankDrive(velocidadIzq, velocidadDer);
    }

    public static void setMotoresVelocidadArcade(double velocidad, double rotacion){
        chasis.arcadeDrive(velocidad, rotacion);
    }

    /*public void cambioVelocidades(boolean cambiarVelocidadAlta, boolean cambiarVelocidadBaja){
        if(cambiarVelocidadAlta == true && cambiarVelocidadBaja == false){//alta
            solenoidVelocidades.set(false);
            velocidadBaja = false;
        }
        else if(cambiarVelocidadAlta = false){//baja
            solenoidVelocidades.set(true);
            relacionUsada = relacionBaja;
            velocidadBaja = true;
        }           
    }*/

    /*public static void moverChasis(boolean tipoManejoTanque, double velocidadIzq_AdelAtra, double velocidadDer_Giro){
        if(tipoManejoTanque == true)
        {
            chasis.tankDrive(velocidadIzq_AdelAtra, velocidadDer_Giro);
            SmartDashboard.putNumber("lado izq", velocidadIzq_AdelAtra);
            SmartDashboard.putNumber("lado der", velocidadDer_Giro);
        }
        else if(tipoManejoTanque == false)
        {
            chasis.arcadeDrive(velocidadIzq_AdelAtra, velocidadDer_Giro);
            SmartDashboard.putNumber("enfrente/atras", velocidadIzq_AdelAtra);
            SmartDashboard.putNumber("giro joystick", velocidadDer_Giro);
        } 
    }*/

     /*==============================Control Driver========================================== */
    /*
     if(controlDriver.getRawButton(1))
     tank = true;
   else if(controlDriver.getRawButton(2))
     tank = false;

   if(tank == true)
   {
     chasis.tankDrive(0.75*controlDriver.getRawAxis(1), 0.75*controlDriver.getRawAxis(5));
     SmartDashboard.putNumber("lado izq", 0.75*controlDriver.getRawAxis(1));
     SmartDashboard.putNumber("lado der", 0.75*controlDriver.getRawAxis(5));
   }
   else if(tank == false)
   {
     chasis.arcadeDrive(0.75*controlDriver.getRawAxis(1), 0.75*controlDriver.getRawAxis(4));
     SmartDashboard.putNumber("enfrente/atras", 0.75*controlDriver.getRawAxis(1));
     SmartDashboard.putNumber("giro joystick", 0.75*controlDriver.getRawAxis(4));
   } 

   if(controlDriver.getPOV() == 90){//alta
     velocidades.set(false);
     relacionUsada = relacionAlta;
     velocidad = false;
   }
   if(controlDriver.getPOV() == 270){//baja
     
     velocidades.set(true);
     relacionUsada = relacionBaja;
     velocidad = true;
   }

*/

}
 