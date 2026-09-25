package com.example.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.data.local.AppDatabase
import com.example.data.model.CommentEntity
import com.example.data.model.CommentStatus
import com.example.data.model.ContactEntity
import com.example.data.model.InquiryEntity
import com.example.data.model.JobCategory
import com.example.data.model.JobOpportunity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EmploymentRepository(private val database: AppDatabase) {

    private val commentDao = database.commentDao()
    private val inquiryDao = database.inquiryDao()
    private val contactDao = database.contactDao()

    companion object {
        const val BUSINESS_EMAIL = "worldpathemploymentservices@gmail.com"
        const val BUSINESS_NAME = "WorldPath Employment Services"

        val VERIFIED_OPPORTUNITIES = listOf(
            JobOpportunity(
                id = "wp-job-101",
                title = "Logistics Operations & Warehouse Specialist",
                employer = "Midwest Intermodal Logistics Hub LLC",
                isEmployerNameDisclosed = true,
                location = "Columbus, OH, United States",
                category = JobCategory.LOGISTICS,
                description = "Responsible for inbound shipment processing, automated inventory scanning, order verification, and compliant pallet staging in a climate-controlled distribution center. Opportunity includes comprehensive safety protocol training and equipment onboarding.",
                requirements = listOf(
                    "Minimum 1 year of verifiable warehouse, supply chain, or inventory experience",
                    "Working/conversational English proficiency (verbal and written safety checklists)",
                    "High school diploma or foreign equivalent educational evaluation",
                    "Ability to lift up to 50 lbs safely and stand for shift durations",
                    "Clean pre-employment background screening and verifiable supervisory references"
                ),
                schedule = "Full-Time (40 hrs/week), Monday to Friday, 7:00 AM – 3:30 PM",
                payInfo = "$21.50 – $24.00 / hour (Paid bi-weekly. Complies with U.S. DOL Prevailing Wage guidelines)",
                isPayVerified = true,
                applicationDeadline = "October 31, 2026",
                applicationInstructions = "Express your interest using the 'I'm Interested' form below. Our advisory team will review qualifications and provide instructions on document pre-screening via email. No fees are ever charged.",
                status = "Actively Vetting Applicants",
                postedDate = "September 12, 2026",
                openingsCount = 6,
                employerVerificationId = "DOL-ETA-9141-OH-2026"
            ),
            JobOpportunity(
                id = "wp-job-102",
                title = "Healthcare Certified Nursing Assistant (CNA) / Patient Care Aide",
                employer = "Memorial Regional Health & Senior Living",
                isEmployerNameDisclosed = true,
                location = "Indianapolis, IN, United States",
                category = JobCategory.HEALTHCARE,
                description = "Assist healthcare team members with non-invasive patient care, monitoring vitals, mobility assistance, nutritional charting, and compassionate patient support in an accredited rehabilitation and long-term care facility.",
                requirements = listOf(
                    "Completed Certified Nursing Assistant (CNA) credential or equivalent international nursing diploma",
                    "Intermediate to professional English communication (IELTS General 6.0+ or equivalent clinical fluency)",
                    "Basic Life Support (BLS) / CPR certification (or willingness to complete prior to start)",
                    "Minimum 1 year hospital, clinic, or residential care facility experience",
                    "Valid government-issued identity documents and verifiable medical school/employer credentials"
                ),
                schedule = "Full-Time (36–40 hrs/week), 12-hour rotating shifts (3 days on, 4 days off)",
                payInfo = "$23.00 – $27.50 / hour + Shift Differential (Overtime eligible at 1.5x)",
                isPayVerified = true,
                applicationDeadline = "November 15, 2026",
                applicationInstructions = "Submit your inquiry with an overview of your medical training and clinical background. Initial candidate review and preliminary questionnaire will be sent to your email.",
                status = "Priority Staffing Need",
                postedDate = "September 15, 2026",
                openingsCount = 8,
                employerVerificationId = "DOL-ETA-9141-IN-8841"
            ),
            JobOpportunity(
                id = "wp-job-103",
                title = "CNC Machine Operator & Precision Metal Fabricator",
                employer = "Authorized Industrial Manufacturing Partner (Non-Disclosure Requested)",
                isEmployerNameDisclosed = false,
                location = "Dallas-Fort Worth Metro, TX, United States",
                category = JobCategory.SKILLED_TRADES,
                description = "Setup, calibrate, and operate 3-axis and 5-axis CNC milling machinery to manufacture precision aerospace and automotive components following technical CAD/CAM blueprints and tolerance specifications.",
                requirements = listOf(
                    "Minimum 2+ years proven operating experience with CNC lathes, mills, or laser cutting machinery",
                    "Demonstrated ability to read engineering drawings and use precision micrometer/caliper instruments",
                    "Vocational technical certificate or secondary apprenticeship completion",
                    "Technical English comprehension for safety standards and operational specifications",
                    "Strong focus on OSHA compliance and precision tolerances (+/- 0.001 inch)"
                ),
                schedule = "Full-Time (40 hrs/week), Monday to Thursday (10-hour shifts, 4-day workweek)",
                payInfo = "$26.00 – $31.00 / hour (Based on verifiable technical skill evaluation)",
                isPayVerified = true,
                applicationDeadline = "December 1, 2026",
                applicationInstructions = "Submit an inquiry with details of your machine tool experience and equipment familiarity. Qualified candidates receive an email requesting technical certifications.",
                status = "Actively Vetting Applicants",
                postedDate = "September 18, 2026",
                openingsCount = 3,
                employerVerificationId = "DOL-ETA-9141-TX-5019"
            ),
            JobOpportunity(
                id = "wp-job-104",
                title = "Hospitality Front Desk & Guest Relations Coordinator",
                employer = "Grand Palms Resort & Conference Center",
                isEmployerNameDisclosed = true,
                location = "Orlando, FL, United States",
                category = JobCategory.HOSPITALITY,
                description = "Deliver professional front-of-house guest services, handle check-in/check-out procedures, manage reservation software, coordinate concierge inquiries, and resolve guest needs in an upscale resort environment.",
                requirements = listOf(
                    "Minimum 1-2 years experience in front desk hospitality, tourism, or international guest relations",
                    "Fluent conversational and written English proficiency (multilingual capabilities a plus)",
                    "Familiarity with property management systems (PMS) and general computer literacy",
                    "Strong interpersonal communication and professional presentation skills",
                    "Clean police clearance certificate and verifiable employer letters"
                ),
                schedule = "Full-Time (40 hrs/week), Flexible rotating shifts including weekends",
                payInfo = "$19.00 – $22.50 / hour + Health & Wellness Benefits Package",
                isPayVerified = true,
                applicationDeadline = "October 25, 2026",
                applicationInstructions = "Submit your interest form detailing customer service experience. Video interview scheduling guidelines will be provided by email.",
                status = "Active Recruitment",
                postedDate = "September 19, 2026",
                openingsCount = 5,
                employerVerificationId = "DOL-ETA-9141-FL-3392"
            ),
            JobOpportunity(
                id = "wp-job-105",
                title = "Agricultural Equipment & Irrigation Operations Technician",
                employer = "Sunfield Agritech & Valley Farms",
                isEmployerNameDisclosed = true,
                location = "Yakima Valley, WA, United States",
                category = JobCategory.AGRICULTURE,
                description = "Monitor automated drip irrigation networks, perform preventive maintenance on commercial farming tractors and harvesters, and maintain precise soil moisture telemetry records for large-scale orchard operations.",
                requirements = listOf(
                    "Minimum 1 year operating agricultural machinery or commercial drip irrigation systems",
                    "Mechanical aptitude for basic diesel engine troubleshooting and pipe fitting",
                    "Basic English comprehension for operating manuals and pesticide application safety signs",
                    "Valid driver's license from home country with clean record",
                    "Ability to work outdoors under varying weather conditions"
                ),
                schedule = "Seasonal to Full-Time (40–48 hrs/week during peak cultivation, overtime paid)",
                payInfo = "$19.75 – $22.00 / hour (Adverse Effect Wage Rate compliant with WA Dept of Labor)",
                isPayVerified = true,
                applicationDeadline = "November 10, 2026",
                applicationInstructions = "Complete the 'I'm Interested' form. Candidates meeting basic equipment experience will be emailed the seasonal operational requirements.",
                status = "Accepting Inquiries",
                postedDate = "September 20, 2026",
                openingsCount = 10,
                employerVerificationId = "DOL-ETA-790A-WA-1104"
            ),
            JobOpportunity(
                id = "wp-job-106",
                title = "Tier-1 Technical Support & IT Helpdesk Associate",
                employer = "Beacon Enterprise Systems LLC",
                isEmployerNameDisclosed = true,
                location = "Phoenix, AZ, United States",
                category = JobCategory.TECH,
                description = "Provide technical troubleshooting for corporate hardware, Windows/macOS operating systems, VPN configurations, and SaaS ticketing. Work in an onsite technical support center assisting enterprise clients.",
                requirements = listOf(
                    "Diploma or Bachelor's in Computer Science, Information Technology, or CompTIA A+ certification",
                    "Fluent spoken and written English communication for technical diagnostics",
                    "Knowledge of Active Directory, Microsoft 365 administration, and remote desktop tools",
                    "Strong problem-solving capability and customer support patience",
                    "Verifiable references and authenticated educational credentials"
                ),
                schedule = "Full-Time (40 hrs/week), Monday to Friday, 8:00 AM – 5:00 PM",
                payInfo = "$24.00 – $28.00 / hour ($50,000 – $58,000 annualized)",
                isPayVerified = true,
                applicationDeadline = "November 30, 2026",
                applicationInstructions = "Submit your inquiry with details of your IT coursework, certifications, and technical troubleshooting history. Response sent via email.",
                status = "Actively Vetting Applicants",
                postedDate = "September 22, 2026",
                openingsCount = 4,
                employerVerificationId = "DOL-ETA-9141-AZ-6621"
            )
        )
    }

    init {
        // Pre-populate sample verified Q&A comments so the user immediately sees how comments and moderation work
        CoroutineScope(Dispatchers.IO).launch {
            if (commentDao.getCommentCount() == 0) {
                val seedComments = listOf(
                    CommentEntity(
                        jobId = "wp-job-101",
                        authorName = "Carlos R.",
                        country = "Mexico",
                        text = "Does the employer provide safety footwear on-site, or should candidates bring certified steel-toe boots?",
                        timestamp = System.currentTimeMillis() - 86400000 * 3,
                        status = CommentStatus.APPROVED.name,
                        adminReply = "Hello Carlos. The employer provides an annual safety equipment allowance and on-site PPE (vests, helmets, gloves). Candidates are asked to bring standard steel-toe boots upon orientation."
                    ),
                    CommentEntity(
                        jobId = "wp-job-101",
                        authorName = "Amara K.",
                        country = "Nigeria",
                        text = "Are international diplomas evaluated through WES or does the employer accept other credential evaluators?",
                        timestamp = System.currentTimeMillis() - 86400000 * 2,
                        status = CommentStatus.APPROVED.name,
                        adminReply = "Hi Amara. Evaluators that are members of NACES (such as WES, ECE, or Josef Silny) are all acceptable for secondary education verification."
                    ),
                    CommentEntity(
                        jobId = "wp-job-102",
                        authorName = "Fatima Z.",
                        country = "Morocco",
                        text = "I have 3 years of hospital nursing experience abroad. Do I need Indiana state licensing before submitting an interest inquiry?",
                        timestamp = System.currentTimeMillis() - 86400000 * 4,
                        status = CommentStatus.APPROVED.name,
                        adminReply = "Good day Fatima. For the initial inquiry stage, your current credentials and degree are evaluated. If selected for an employer interview, our advisory team will provide step-by-step guidance on Indiana state aide endorsement."
                    ),
                    CommentEntity(
                        jobId = "wp-job-103",
                        authorName = "Arjun M.",
                        country = "India",
                        text = "What CNC controller brands are primarily used in this facility? Fanuc or Siemens?",
                        timestamp = System.currentTimeMillis() - 86400000 * 1,
                        status = CommentStatus.APPROVED.name,
                        adminReply = "Hello Arjun. The shop floor primarily utilizes Fanuc Series 31i and Haas NGC controllers. Familiarity with either is considered relevant."
                    )
                )
                commentDao.insertAll(seedComments)
            }
        }
    }

    fun getOpportunityById(id: String): JobOpportunity? {
        return VERIFIED_OPPORTUNITIES.find { it.id == id }
    }

    fun searchOpportunities(query: String, category: JobCategory): List<JobOpportunity> {
        return VERIFIED_OPPORTUNITIES.filter { opp ->
            val matchesCategory = (category == JobCategory.ALL || opp.category == category)
            val matchesQuery = query.isBlank() ||
                    opp.title.contains(query, ignoreCase = true) ||
                    opp.location.contains(query, ignoreCase = true) ||
                    opp.employer.contains(query, ignoreCase = true) ||
                    opp.description.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    // Comments & Moderation
    fun getApprovedComments(jobId: String): Flow<List<CommentEntity>> =
        commentDao.getApprovedCommentsForJob(jobId)

    fun getAllCommentsForJob(jobId: String): Flow<List<CommentEntity>> =
        commentDao.getAllCommentsForJob(jobId)

    fun getAllCommentsForModeration(): Flow<List<CommentEntity>> =
        commentDao.getAllCommentsForModeration()

    suspend fun addComment(jobId: String, author: String, country: String, text: String): Long {
        // Automatic basic safety check: if containing spam or suspicious financial requests, flag as pending review
        val lower = text.lowercase()
        val isSuspicious = lower.contains("send money") || lower.contains("whatsapp") ||
                lower.contains("telegram") || lower.contains("fee") || lower.contains("crypto")

        val initialStatus = if (isSuspicious) CommentStatus.PENDING_REVIEW.name else CommentStatus.APPROVED.name

        val comment = CommentEntity(
            jobId = jobId,
            authorName = author.trim(),
            country = country.trim(),
            text = text.trim(),
            timestamp = System.currentTimeMillis(),
            status = initialStatus,
            adminReply = null
        )
        return commentDao.insertComment(comment)
    }

    suspend fun updateCommentStatus(id: Long, status: CommentStatus) {
        commentDao.updateCommentStatus(id, status.name)
    }

    suspend fun addAdminReply(id: Long, reply: String) {
        commentDao.updateAdminReply(id, reply.trim())
    }

    suspend fun deleteComment(id: Long) {
        commentDao.deleteComment(id)
    }

    // Inquiries ("I'm Interested")
    fun getAllInquiries(): Flow<List<InquiryEntity>> = inquiryDao.getAllInquiries()

    suspend fun submitInquiry(inquiry: InquiryEntity): Long {
        return inquiryDao.insertInquiry(inquiry)
    }

    // Contact Messages
    fun getAllContactMessages(): Flow<List<ContactEntity>> = contactDao.getAllContactMessages()

    suspend fun submitContactMessage(contact: ContactEntity): Long {
        return contactDao.insertContact(contact)
    }

    // Email Intent Helper
    fun createInquiryEmailIntent(inquiry: InquiryEntity): Intent {
        val subject = "Employment Inquiry: ${inquiry.jobTitle} - ${inquiry.fullName}"
        val body = """
            Dear WorldPath Employment Services Advisory Team,

            I am submitting an expression of interest for the following opportunity:
            Opportunity: ${inquiry.jobTitle} (ID: ${inquiry.jobId})
            
            Applicant Information:
            - Full Name: ${inquiry.fullName}
            - Email: ${inquiry.email}
            - Country of Residence: ${inquiry.country}
            - Phone: ${inquiry.phone.ifBlank { "Not provided" }}

            Message / Background Overview:
            ${inquiry.message}

            Consent Confirmation:
            I confirm that I have consented to be contacted by WorldPath Employment Services regarding this opportunity inquiry.

            Thank you.
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$BUSINESS_EMAIL")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(BUSINESS_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        return intent
    }

    fun createGeneralContactEmailIntent(name: String, email: String, subject: String, message: String): Intent {
        val emailSubject = "[WorldPath Contact Inquiry] $subject"
        val body = """
            From: $name ($email)
            To: WorldPath Employment Services

            Message:
            $message
        """.trimIndent()

        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$BUSINESS_EMAIL")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(BUSINESS_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
    }
}
