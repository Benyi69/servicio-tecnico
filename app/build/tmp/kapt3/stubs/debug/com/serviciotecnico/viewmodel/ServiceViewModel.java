package com.serviciotecnico.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010\u001b\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019J\u000e\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u001e\u0010!\u001a\u00020\u00172\u0016\b\u0002\u0010\"\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010$\u0012\u0004\u0012\u00020\u00170#J\u0006\u0010%\u001a\u00020&J\b\u0010\'\u001a\u00020&H\u0002J\b\u0010(\u001a\u00020&H\u0002J\b\u0010)\u001a\u00020&H\u0002J\b\u0010*\u001a\u00020&H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000f\u00a8\u0006+"}, d2 = {"Lcom/serviciotecnico/viewmodel/ServiceViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/serviciotecnico/data/repository/ServiceRepository;", "context", "Landroid/content/Context;", "(Lcom/serviciotecnico/data/repository/ServiceRepository;Landroid/content/Context;)V", "_errors", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/serviciotecnico/model/TicketFormErrors;", "_form", "Lcom/serviciotecnico/model/TicketFormState;", "errors", "Lkotlinx/coroutines/flow/StateFlow;", "getErrors", "()Lkotlinx/coroutines/flow/StateFlow;", "form", "getForm", "tickets", "", "Lcom/serviciotecnico/model/ServiceTicket;", "getTickets", "setClienteNombre", "", "v", "", "setDescripcion", "setFotoUri", "setMarca", "setModelo", "setPatente", "setTelefono", "setTipoVehiculo", "submitTicket", "onComplete", "Lkotlin/Function1;", "", "validarFormulario", "", "validateDescripcion", "validateNombre", "validatePatente", "validateTelefono", "app_debug"})
public final class ServiceViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.serviciotecnico.data.repository.ServiceRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.serviciotecnico.model.TicketFormState> _form = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.serviciotecnico.model.TicketFormState> form = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.serviciotecnico.model.TicketFormErrors> _errors = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.serviciotecnico.model.TicketFormErrors> errors = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.serviciotecnico.model.ServiceTicket>> tickets = null;
    
    public ServiceViewModel(@org.jetbrains.annotations.NotNull()
    com.serviciotecnico.data.repository.ServiceRepository repo, @org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.serviciotecnico.model.TicketFormState> getForm() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.serviciotecnico.model.TicketFormErrors> getErrors() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.serviciotecnico.model.ServiceTicket>> getTickets() {
        return null;
    }
    
    public final void setClienteNombre(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void setTelefono(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void setTipoVehiculo(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void setMarca(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void setModelo(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void setPatente(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void setDescripcion(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final void setFotoUri(@org.jetbrains.annotations.Nullable()
    java.lang.String v) {
    }
    
    private final boolean validateNombre() {
        return false;
    }
    
    private final boolean validateTelefono() {
        return false;
    }
    
    private final boolean validatePatente() {
        return false;
    }
    
    private final boolean validateDescripcion() {
        return false;
    }
    
    public final boolean validarFormulario() {
        return false;
    }
    
    public final void submitTicket(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onComplete) {
    }
}