#pragma once
#include "Image.h"
#include "Sound.h"
#include "Wave.h"
#include <memory>


/**
 * \brief Beeinhaltet Datenstrukturen zur Umwandlung von Bildinformationen in Toene
 */
namespace Processing
{

	/**
	 * \brief Verfuegbare Algorithmen
	 */
	enum class ProcessingAlgorithm
	{
		LR_SCAN = 0,
		LR_SCAN_NO_THRESHOLD,
		UD_SCAN,
		UD_SCAN_NO_THRESHOLD,
		TRIPLET,
		TRIPLET_JMP,
		UD_LR_SCAN
		//TODO Mehr implementieren
	};

	/**
	 * \brief Grundeinstellungen fuer die Tonerzeugung
	 */
	struct ProcessingSettings
	{
		double audioVolume = 50;
		double samplingFrequency = 44100;
		size_t samples = 128;
	};


	/**
	 * \brief Zusaetzliche Einstellungen spezifisch fuer die verschienden Algorithmen
	 */
	struct AdditionalSettings
	{
		uint64_t settings1 = 0;
		uint64_t settings2 = 0;
		uint64_t settings3 = 0;
		uint64_t settings4 = 0;
	};


	/**
	 * \brief Klasse zur Umwandlung von Bilddaten in Tondaten 
	 */
	class Processing
	{
	private:
		std::unique_ptr<Image> image;
		std::unique_ptr<Sound> sound;
		std::unique_ptr<Wave> wave;


		/**
		 * \brief Bild wird von Links nach Rechts spaltenweise durchgescannt. Jede Zeile steht fuer eine Frequenz. 
		 * Je intensiver RGB vom Pixel desto hoeher die Amplitude der jeweiligen Frequenz. Pixel werden nur betrachtet, 
		 * wenn der Durschnitt von RGB > Aktivierungsschwelle ist
		 * 
		 * \param additionalSettings:
		 *  Settings1: Maximale Frequenz
		 *  Settings2: Aktivierungsschwelle fuer Pixel
		 *  Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
		 */
		void lrScan(const AdditionalSettings& additionalSettings); 


		/**
		* \brief Bild wird von Links nach Rechts spaltenweise durchgescannt. Jede Zeile steht fuer eine Frequenz.
		* Je intensiver RGB vom Pixel desto hoeher die Amplitude der jeweiligen Frequenz. Keine Aktivierungsgrenze
		*
		* \param additionalSettings:
		*  Settings1: Maximale Frequenz
		*  Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
		*/
		void lrScan_no_threshold(const AdditionalSettings& additionalSettings); 


		/**
		* \brief Bild wird von Oben nach Unten zweilenweise durchgescannt. Jede Spalte steht fuer eine Frequenz.
		* Je intensiver RGB vom Pixel desto hoeher die Amplitude der jeweiligen Frequenz. Pixel werden nur betrachtet, 
		* wenn der Durschnitt von RGB > Aktivierungsschwelle ist
		*
		* \param additionalSettings:
		*  Settings1: Maximale Frequenz
		*  Settings2: Aktivierungsschwelle fuer Pixel
		*  Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
		*/
		void udScan(const AdditionalSettings& additionalSettings); 


		/**
		* \brief Bild wird von Oben nach Unten zweilenweise durchgescannt. Jede Spalte steht fuer eine Frequenz.
		* Je intensiver RGB vom Pixel desto hoeher die Amplitude der jeweiligen Frequenz. Keine Aktivierungsschwelle
		*
		* \param additionalSettings:
		*  Settings1: Maximale Frequenz
		*  Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
		*/
		void udScan_no_threshold(const AdditionalSettings& additionalSettings); 


		/**
		 * \brief Bild wird immer um 3 Byte (Triplet) oder Vielfache durchgesprungen. Jede Farbe hat eine Eigenschaft bei der Tonerzeugung 
		 * Rot: Lautstaerke ; Gruen: Tonhoehe; Blau: Tondauer
		 * 
		 * \param additionalSettings:
		 * Settings2: Tondauer abhaengig von dem Blauanteil
		 * Settings3: Anzahl der Triplets die man pro Frequenz ueberspringen soll
		 * Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
		 */
		void triplet(const AdditionalSettings& additionalSettings); 


		/**
		 * \brief Bild wird mit einem Offset abhaengig von der Farbe durchgesprungen
		 * Rot: Lautstarke, Gruen: Tonhoehe; Blau: keine Funktion
		 * 
		 * \param additionalSettings:
		 * Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
		 */
		void triplet_jmp(const AdditionalSettings& additionalSettings); 


		/**
		 * \brief Das Bild wird von oben nach unten und von links nach rechts gleichzeitig gescannt. 
		 * Die Senkrechte zur X-Achse und die Parallele erzeugen beide jeweils einen Ton. 
		 * Jeder Ton wird abhaengig von jeweiligen Farbanteil erzeugt.
		 * Rot bestimmt die Frequenz, Gruen bestimmt die Lautstaerke und der gemeinsame Blauanteil die Dauer der beiden Frequenzen.
		 * 
		 * \param additionalSettings:
		 * Settings1: Max Frequenz
		 * Settings2: Tondauer abhaengig von dem Blauanteil
		 * Settings4: Wenn != 0, dann werden die Pixel invertiert betrachtet
		 */
		void ud_lr_scan(const AdditionalSettings& additionalSettings);	

	public:
		/**
		 * \brief Starte die Umwandlung eines Bildes in eine Audiodatei, anhand eines vorgegebenen Algorithmus.
		 * \param imageFile: Dateiname vom Bild
		 * \param outputFile: Dateiname der zu erstellenden Audiodatei
		 * \param algorithm: Algorithmus der fuer die Umwandlung genutzt wird
		 * \param settings: Allgemeine Einstellungen fuer die Tonerzeugung
		 * \param additionalSettings: Spezifische Einstellungen fuer den jeweiligen Algorithmus
		 */
		void start(const std::string& imageFile, const std::string& outputFile, ProcessingAlgorithm algorithm,
		           const ProcessingSettings& settings, const AdditionalSettings& additionalSettings);

		/**
		 * \brief Standardkonstruktor
		 */
		Processing() = default;

		/**
		 * \brief Kopieren nicht moeglich
		 */
		Processing(const Processing & other) = delete;

		/**
		 * \brief Zuweisung nicht moeglich
		 */
		Processing& operator=(const Processing & other) = delete;

		/**
		 * \brief Movezuweisung nicht moeglich
		 */
		Processing& operator=(Processing&& other) = delete;
	};
}
