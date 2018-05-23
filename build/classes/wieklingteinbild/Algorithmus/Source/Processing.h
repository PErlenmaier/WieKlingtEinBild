#pragma once
#include "Image.h"
#include "Sound.h"
#include "Wave.h"
#include <memory>


namespace Processing
{
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

	struct ProcessingSettings
	{
		double audioVolume = 50;
		double samplingFrequency = 44100;
		size_t samples = 128;
	};

	struct AdditionalSettings
	{
		uint64_t settings1 = 0;
		uint64_t settings2 = 0;
		uint64_t settings3 = 0;
		uint64_t settings4 = 0;
	};

	class Processing
	{
	private:
		std::unique_ptr<Image> image;
		std::unique_ptr<Sound> sound;
		std::unique_ptr<Wave> wave;

		void lrScan(const AdditionalSettings& additionalSettings); //Left Right Scan 
		void lrScan_no_threshold(const AdditionalSettings& additionalSettings); //Left Right Scan ohne Aktivierungsgrenze

		void udScan(const AdditionalSettings& additionalSettings); //Up Down Scan
		void udScan_no_threshold(const AdditionalSettings& additionalSettings); //Up Down Scan ohne Aktivierungsgrenze

		void triplet(const AdditionalSettings& additionalSettings); //triplet scan Laenge der Frequenz von Blau abhaengig
		void triplet_jmp(const AdditionalSettings& additionalSettings); //triplet scan Offset zum naechsten Triplet von Blau abhaengig

		void ud_lr_scan(const AdditionalSettings& additionalSettings);	//von links nach rechts und gleichzeitig von oben nach unten scannen
	public:
		void start(const std::string& imageFile, const std::string& outputFile, ProcessingAlgorithm algorithm,
		           const ProcessingSettings& settings, const AdditionalSettings& additionalSettings);


		Processing() = default;
		Processing(const Processing & other) = delete;
		Processing& operator=(const Processing & other) = delete;
		Processing& operator=(Processing&& other) = delete;
	};
}
